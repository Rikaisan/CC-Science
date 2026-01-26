package com.rikaisan.ccscience.peripheral;

import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class EntityRadarPeripheral implements IPeripheral {
    private final EntityRadarBlockEntity radar;
    private final AttachedComputerSet computers = new AttachedComputerSet();

    public EntityRadarPeripheral(EntityRadarBlockEntity radar) {
        this.radar = radar;
    }

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
        return mapScan(radar.getLastScan());
    }

    public boolean isAttached() {
        return computers.hasComputers();
    }

    public void updateScanData(List<EntityRadarBlockEntity.ScanEntry> scan) {
        computers.forEach((computer) -> computer.queueEvent("entity_radar_sweep", computer.getAttachmentName(), mapScan(scan)));
    }

    private static List<Map<String, Object>> mapScan(List<EntityRadarBlockEntity.ScanEntry> scan) {
        return scan.stream().<Map<String, Object>>map(e -> {
            final Vec3 pos = e.pos();
            return Map.of(
                    "name", e.name(),
                    "type", e.type().toString(),
                    "pos", DoubleList.of(pos.x, pos.y, pos.z)
            );
        }).toList();
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return this == other;
    }
}
