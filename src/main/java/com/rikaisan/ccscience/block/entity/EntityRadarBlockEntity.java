package com.rikaisan.ccscience.block.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EntityRadarBlockEntity extends BlockEntity {

    private Vec3i boxRadius = new Vec3i(8, 8, 8);

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    public List<Entity> getSurroundingEntities() {
        if (this.getLevel() != null) {
            return this.getLevel().getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
        }
        return new ArrayList<Entity>();
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
