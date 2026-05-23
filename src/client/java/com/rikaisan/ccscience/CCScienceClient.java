package com.rikaisan.ccscience;

import com.rikaisan.ccscience.menu.ModMenuType;
import com.rikaisan.ccscience.menu.UpgradeMenu;
import com.rikaisan.ccscience.screen.UpgradeScreen;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class CCScienceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.<UpgradeMenu, UpgradeScreen>register(ModMenuType.UPGRADE_MENU, (gui, inventory, title) -> new UpgradeScreen(gui, inventory.player, title));
	}
}