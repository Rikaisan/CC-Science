package com.rikaisan.ccscience.block.entity;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.rikaisan.ccscience.peripheral.EntityRadarPeripheral;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EntityRadarBlockEntity extends BlockEntity {
    private Set<EntityRadarPeripheral> peripherals = ConcurrentHashMap.newKeySet();
    public int tickCounter = 0;

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    private List<Entity> getEntitiesInRadius(Level world, int radius) {
        radius = Math.abs(radius);
        final Vec3i boxRadius = new Vec3i(radius, radius, radius);
        return world.getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
    }

    public void attachPeripheral(EntityRadarPeripheral peripheral) {
        this.peripherals.add(peripheral);
    }

    public void detachPeripheral(EntityRadarPeripheral peripheral) {
        this.peripherals.remove(peripheral);
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, EntityRadarBlockEntity entity) {
        entity.tick(world, blockPos, blockState);
    }

    public void tick(Level world, BlockPos blockPos, BlockState blockState) {
        this.tickCounter++;
        if (this.tickCounter >= 20) {
            sweep(world, blockPos, blockState);
            this.tickCounter = 0;
        }
    }

    public void sweep(Level world, BlockPos blockPos, BlockState blockState) {
        peripherals.forEach((peripheral) -> peripheral.updateScanData(getEntitiesInRadius(world, 8), blockPos));
    }
    
    @Override
    protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
    }
}
