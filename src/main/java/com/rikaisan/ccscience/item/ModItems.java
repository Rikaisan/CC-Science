package com.rikaisan.ccscience.item;

import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModItems {
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, "item_group"));
    public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
    .icon(() -> new ItemStack(ModBlocks.ENTITY_RADAR.asItem()))
    .title(Component.translatable(ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, "item_group").toLanguageKey()))
    .build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP_KEY, ITEM_GROUP);
    }

    public static Item register(String id, Item item) {
        ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, id);
        return Registry.register(BuiltInRegistries.ITEM, itemID, item);
    }
}