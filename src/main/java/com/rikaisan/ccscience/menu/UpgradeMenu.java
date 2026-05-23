package com.rikaisan.ccscience.menu;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class UpgradeMenu extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 2;

    public UpgradeMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public UpgradeMenu(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(ModMenuType.UPGRADE_MENU, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));
        WPlainPanel root = ContainerMenuHelper.CreateSingleRowMenuWithInventory(INVENTORY_SIZE, this.blockInventory, this.playerInventory);
        setRootPanel(root);
        root.validate(this);
    }
}