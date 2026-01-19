package com.rikaisan.ccscience.peripheral;

import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class EntityRadarPeripheral implements GenericPeripheral {
    @Override
    public @NonNull String id() {
        return ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, "entity_radar").toString();
    }

    @LuaFunction
    public Map<String, List<Float>> scan(EntityRadarBlockEntity radar) {
        Map<String, List<Float>> entities = new HashMap<>();
        final Vec3i center = radar.getBlockPos();

        for (Entity entity : radar.getSurroundingEntities()) {
            Vec3 relativePos = entity.position().subtract(center.getX(), center.getY(), center.getZ());
            List<Float> pos = Arrays.asList((float)relativePos.x, (float)relativePos.y, (float)relativePos.z);
            entities.put(entity.getDisplayName().getString(), pos);
        }

        return entities;
    }
}
