package com.rikaisan.ccscience.datagen;

import com.rikaisan.ccscience.block.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        var model = ModelTemplates.CUBE_ORIENTABLE.create(
            ModBlocks.ENTITY_RADAR,
            TextureMapping.orientableCubeOnlyTop(ModBlocks.ENTITY_RADAR),
            blockStateModelGenerator.modelOutput
        );
        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(ModBlocks.ENTITY_RADAR, Variant.variant().with(VariantProperties.MODEL, model))
                .with(createHorizontalFacingDispatch())
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {}
    
    private static PropertyDispatch createHorizontalFacingDispatch() {
        var dispatch = PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING);
        for (var direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            dispatch.select(direction, Variant.variant().with(VariantProperties.Y_ROT, toYAngle(direction)));
        }
        return dispatch;
    }

    private static VariantProperties.Rotation toYAngle(Direction direction) {
        return switch (direction) {
            default -> VariantProperties.Rotation.R0;
            case Direction.NORTH -> VariantProperties.Rotation.R0;
            case Direction.SOUTH -> VariantProperties.Rotation.R180;
            case Direction.EAST -> VariantProperties.Rotation.R90;
            case Direction.WEST -> VariantProperties.Rotation.R270;
        };
    }
}
