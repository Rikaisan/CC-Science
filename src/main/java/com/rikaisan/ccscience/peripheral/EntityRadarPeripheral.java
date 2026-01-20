package com.rikaisan.ccscience.peripheral;

import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class EntityRadarPeripheral implements GenericPeripheral {
    @Override
    public @NonNull String id() {
        return ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, "entity_radar").toString();
    }

    @LuaFunction
    public List<Map<String, Object>> scan(EntityRadarBlockEntity radar) {
        List<Map<String, Object>> entities = new ArrayList<>();
        final BlockPos CENTER = radar.getBlockPos();

        for (Entity entity : radar.getSurroundingEntities()) {
            final Vec3 relativePos = entity.position().subtract(CENTER.getX(), CENTER.getY(), CENTER.getZ());
            entities.add(Map.of(
                "name", entity.getDisplayName().getString(),
                "type", EntityType.getKey(entity.getType()).toString(),
                "pos", DoubleList.of(relativePos.x, relativePos.y, relativePos.z)
            ));
        }

        return entities;
    }
}
