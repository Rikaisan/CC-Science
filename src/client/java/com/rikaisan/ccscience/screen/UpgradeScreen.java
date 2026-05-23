package com.rikaisan.ccscience.screen;

import com.rikaisan.ccscience.menu.UpgradeMenu;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class UpgradeScreen extends CottonInventoryScreen<UpgradeMenu> {
    public UpgradeScreen(UpgradeMenu gui, Player player, Component title) {
        super(gui, player, title);
    }
}
