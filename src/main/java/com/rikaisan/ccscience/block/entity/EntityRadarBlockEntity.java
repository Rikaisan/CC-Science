package com.rikaisan.ccscience.block.entity;

import java.util.List;

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
    private final EntityRadarPeripheral peripheral = new EntityRadarPeripheral();
    public int tickCounter = 0;

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    private List<Entity> getEntitiesInRadius(Level world, int radius) {
        radius = Math.abs(radius);
        final Vec3i boxRadius = new Vec3i(radius, radius, radius);
        return world.getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
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

        setChanged();
    }

    public void sweep(Level world, BlockPos blockPos, BlockState blockState) {
        peripheral.updateScanData(getEntitiesInRadius(world, 8), blockPos);
    }

    public EntityRadarPeripheral getPeripheral() {
        return this.peripheral;
    }
    
    @Override
    protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        nbt.putInt("tick_counter", tickCounter);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        tickCounter = nbt.getInt("tick_counter");
    }
}
