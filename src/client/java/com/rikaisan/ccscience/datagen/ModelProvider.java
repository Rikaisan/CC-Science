package com.rikaisan.ccscience.datagen;

import com.rikaisan.ccscience.block.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TexturedModel;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialBlock(ModBlocks.ENTITY_RADAR, TexturedModel.ORIENTABLE_ONLY_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {}
    
}
