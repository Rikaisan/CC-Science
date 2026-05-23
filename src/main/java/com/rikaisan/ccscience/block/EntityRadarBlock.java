package com.rikaisan.ccscience.block;

import com.mojang.serialization.MapCodec;
import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.block.entity.CCScienceBlockEntityType;
import com.rikaisan.ccscience.block.entity.EntityRadarBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EntityRadarBlock extends HorizontalFacingPeripheralBlock {
    public static final String NAME = "entity_radar";
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, NAME);
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
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(blockState.getMenuProvider(level, blockPos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected MapCodec<? extends EntityRadarBlock> codec() {
        return CODEC;
    }
}
