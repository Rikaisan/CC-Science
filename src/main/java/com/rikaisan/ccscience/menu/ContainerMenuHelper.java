package com.rikaisan.ccscience.menu;

import io.github.cottonmc.cotton.gui.impl.client.NarrationMessages;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public class ContainerMenuHelper {
    public static int DEFAULT_INSET = 7;
    public static int LABEL_SIZE = 10;
    public static int SLOT_SIZE = 18;
    public static Insets LEBELED_INSETS = new Insets(DEFAULT_INSET + LABEL_SIZE, DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET);

    public static WPlainPanel CreateSingleRowMenuWithInventory(int size, Container blockInventory, Inventory playerInventory) {
        size = Math.clamp(size, 1, 9);
        WPlainPanel root = new WPlainPanel();
        root.setInsets(LEBELED_INSETS);

        WGridPanel upgradeSlots = new WGridPanel(SLOT_SIZE / 2);
        for (int i = 0; i < size; i++) {
            WItemSlot itemSlot = WItemSlot.of(blockInventory, i);
            upgradeSlots.add(itemSlot, 9 - size + i * 2, 0); // Center slots
        }
        root.add(upgradeSlots, 0, 0);

        root.add(ContainerMenuHelper.CreateFixedPlayerInventoryPanel(playerInventory), 0, upgradeSlots.getHeight());
        return root;
    }

    // The default createPlayerInventoryPanel some things misaligned when compared to the Vanilla containers, this fixes those.
    // Specifically, this method moves the label one pixel to the right and the slots one pixel up.
    public static WPlainPanel CreateFixedPlayerInventoryPanel(Inventory playerInventory) {
        WPlainPanel root = new WPlainPanel();

        WLabel label = new WLabel(playerInventory.getDisplayName());
		label.setSize(9 * SLOT_SIZE, 10);
        root.add(label, 1, 0, label.getWidth(), label.getHeight());
        int y = label.getHeight();

		WItemSlot inventory = WItemSlot.ofPlayerStorage(playerInventory);
		WItemSlot hotbar = new WItemSlot(playerInventory, 0, 9, 1, false) {
			@Override
			protected Component getNarrationName() {
				return NarrationMessages.Vanilla.HOTBAR;
			}
		};
		root.add(inventory, 0, y);
		root.add(hotbar, 0, y + 58);

        root.setInsets(new Insets(3, 0, 0, 0));

        return root;
    }

    public static Component GetTranslatableMenuName(ResourceLocation baseLocation) {
        return Component.translatable(Util.makeDescriptionId("menu", baseLocation));
    }
}
