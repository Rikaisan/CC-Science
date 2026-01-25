package com.rikaisan.ccscience.block;

import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.item.ModItems;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {
    public static final Block ENTITY_RADAR = register("entity_radar", new EntityRadarBlock(Properties.of().sound(SoundType.STONE).strength(1.0f)), true);

    public static void initialize() {

    }

	public static Block register(String name, Block block, boolean shouldRegisterItem) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, name);

		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

}
