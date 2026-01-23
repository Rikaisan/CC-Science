package com.rikaisan.ccscience.block.entity;

import com.rikaisan.ccscience.CCScience;
import com.rikaisan.ccscience.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CCScienceBlockEntityType {
    public static final BlockEntityType<EntityRadarBlockEntity> ENTITY_RADAR = register(
        "entity_radar",
        BlockEntityType.Builder.of(
            EntityRadarBlockEntity::new,
            ModBlocks.ENTITY_RADAR)
        .build());
    
    public static void initialize() {}

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(CCScience.MOD_ID, path), blockEntityType);
    }
}
