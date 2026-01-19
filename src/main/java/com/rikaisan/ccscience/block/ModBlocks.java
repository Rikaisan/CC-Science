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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final Block ENTITY_RADAR = register("entity_radar", new EntityRadar(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)), true);

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.ITEM_GROUP_KEY).register(group -> {
            group.accept(ENTITY_RADAR.asItem());
        });
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
