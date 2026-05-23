package com.rikaisan.ccscience.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {
    public static final Block ENTITY_RADAR = register(EntityRadarBlock.LOCATION, new EntityRadarBlock(Properties.of().sound(SoundType.STONE).strength(1.0f)), true);

    public static void initialize() {}

	public static Block register(ResourceLocation location, Block block, boolean shouldRegisterItem) {
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, location, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, location, block);
	}

}
