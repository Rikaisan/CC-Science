package com.rikaisan.ccscience.block;

import com.mojang.serialization.MapCodec;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class EntityRadarBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final MapCodec<EntityRadarBlock> CODEC = simpleCodec(EntityRadarBlock::new);

    protected EntityRadarBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> properties) {
        properties.add(FACING);
    }

    @Override
    public final BlockState getStateForPlacement(BlockPlaceContext placement) {
        return defaultBlockState().setValue(FACING, placement.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EntityRadarBlockEntity(pos, state);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends EntityRadarBlock> codec() {
        return CODEC;
    }
}
