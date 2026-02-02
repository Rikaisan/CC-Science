package com.rikaisan.ccscience.block;

import com.mojang.serialization.MapCodec;
import com.rikaisan.ccscience.block.entity.CCScienceBlockEntityType;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EntityRadarBlock extends HorizontalFacingPeripheralBlock {
    
    public static final MapCodec<EntityRadarBlock> CODEC = simpleCodec(EntityRadarBlock::new);

    protected EntityRadarBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EntityRadarBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, CCScienceBlockEntityType.ENTITY_RADAR, EntityRadarBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends EntityRadarBlock> codec() {
        return CODEC;
    }
}
