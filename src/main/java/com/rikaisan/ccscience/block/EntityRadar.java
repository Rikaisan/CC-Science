package com.rikaisan.ccscience.block;

import com.mojang.serialization.MapCodec;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EntityRadar extends BaseEntityBlock {

    public static final MapCodec<EntityRadar> CODEC = simpleCodec(EntityRadar::new);

    protected EntityRadar(Properties settings) {
        super(settings);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EntityRadarBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
