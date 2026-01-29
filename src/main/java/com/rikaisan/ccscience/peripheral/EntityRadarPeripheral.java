package com.rikaisan.ccscience.peripheral;


import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class EntityRadarPeripheral implements IPeripheral {
    private final AttachedComputerSet computers = new AttachedComputerSet();
    private List<Map<String, Object>> scannedEntities = List.of();

    public EntityRadarPeripheral() {}

    @Override
    public @NonNull String getType() {
        return "entity_radar";
    }

    @Override
    public void attach(IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        computers.remove(computer);
    }

    @LuaFunction(mainThread = true)
    public List<Map<String, Object>> getLastScan() {
        return this.scannedEntities;
    }

    public void updateScanData(List<Entity> newEntityList, BlockPos originBlock) {
        List<Map<String, Object>> entities = new ArrayList<>();

        Vec3 origin = originBlock.getBottomCenter();

        for (Entity entity : newEntityList) {
            final Vec3 relativePos = entity.position().subtract(origin);
            entities.add(Map.of(
                "name", entity.getDisplayName().getString(),
                "type", EntityType.getKey(entity.getType()).toString(),
                "pos", DoubleList.of(relativePos.x, relativePos.y, relativePos.z)
            ));
        }
        
        this.scannedEntities = entities;
        computers.forEach((computer) -> computer.queueEvent("entity_radar_sweep", computer.getAttachmentName(), entities));
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other == this;
    }
}
