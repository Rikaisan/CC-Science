package com.rikaisan.ccscience.block.entity;

import java.util.List;

import com.rikaisan.ccscience.peripheral.EntityRadarPeripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
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
    private final EntityRadarPeripheral peripheral = new EntityRadarPeripheral(this);
    private int sweepCounter = 0;

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    private List<Entity> getEntitiesInRadius(Level world, int radius) {
        if(radius < 0)
            throw new IllegalArgumentException("Radius may not be negative");
        final Vec3i boxRadius = new Vec3i(radius, radius, radius);
        return world.getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, EntityRadarBlockEntity entity) {
        entity.tick(world, blockPos, blockState);
    }

    public void tick(Level world, BlockPos blockPos, BlockState blockState) {
        sweepCounter++;

        setChanged();

        if(!peripheral.isAttached() || sweepCounter < 20)
            return;

        sweep(world, blockPos, blockState);
        sweepCounter = 0;
    }

    public void sweep(Level world, BlockPos blockPos, BlockState blockState) {
        peripheral.updateScanData(getEntitiesInRadius(world, 8), blockPos);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        nbt.putInt("sweep_counter", sweepCounter);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        sweepCounter = nbt.getInt("sweep_counter");
    }
    public IPeripheral peripheral() {
        return peripheral;
    }
}
