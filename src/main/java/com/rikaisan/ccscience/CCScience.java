package com.rikaisan.ccscience;

import com.rikaisan.ccscience.block.ModBlocks;
import com.rikaisan.ccscience.block.entity.CCScienceBlockEntityType;
import com.rikaisan.ccscience.item.ModItems;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCScience implements ModInitializer {
    public static final String MOD_ID = "cc-science";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loaded CC: Science!");

        ModItems.initialize();
        ModBlocks.initialize();
        CCScienceBlockEntityType.initialize();

        registerPeripherals();
    }

    private void registerPeripherals() {
        PeripheralLookup.get().registerForBlockEntity((blockEntity, direction) -> blockEntity.getPeripheral(), CCScienceBlockEntityType.ENTITY_RADAR);
    }
}