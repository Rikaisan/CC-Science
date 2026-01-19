package com.rikaisan.ccscience;

import com.rikaisan.ccscience.block.ModBlocks;
import com.rikaisan.ccscience.block.entity.CCScienceBlockEntityType;
import com.rikaisan.ccscience.item.ModItems;
import com.rikaisan.ccscience.peripheral.EntityRadarPeripheral;
import dan200.computercraft.api.ComputerCraftAPI;

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

        ComputerCraftAPI.registerGenericSource(new EntityRadarPeripheral());
	}
}