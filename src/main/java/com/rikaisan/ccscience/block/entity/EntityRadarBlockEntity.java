package com.rikaisan.ccscience.block.entity;

import java.util.List;

import com.rikaisan.ccscience.block.EntityRadarBlock;
import com.rikaisan.ccscience.menu.ContainerMenuHelper;
import com.rikaisan.ccscience.menu.UpgradeMenu;
import com.rikaisan.ccscience.peripheral.EntityRadarPeripheral;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EntityRadarBlockEntity extends BaseContainerBlockEntity {
    private final EntityRadarPeripheral PERIPHERAL = new EntityRadarPeripheral();
    private final int UPGRADE_SLOTS = 2;
    private NonNullList<ItemStack> upgrades = NonNullList.withSize(UPGRADE_SLOTS, ItemStack.EMPTY);
    public int tickCounter = 0;

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    private List<Entity> getEntitiesInRadius(Level world, int radius) {
        radius = Math.abs(radius);
        final Vec3i boxRadius = new Vec3i(radius, radius, radius);
        return world.getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, EntityRadarBlockEntity entity) {
        entity.tick(world, blockPos, blockState);
    }

    public void tick(Level world, BlockPos blockPos, BlockState blockState) {
        this.tickCounter++;
        if (this.tickCounter >= 20) {
            sweep(world, blockPos, blockState);
            this.tickCounter = 0;
        }

        setChanged();
    }

    public void sweep(Level world, BlockPos blockPos, BlockState blockState) {
        this.PERIPHERAL.updateScanData(getEntitiesInRadius(world, 8), blockPos);
    }

    public EntityRadarPeripheral getPeripheral() {
        return this.PERIPHERAL;
    }
    
    @Override
    protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        ContainerHelper.saveAllItems(nbt, upgrades, registryLookup);
        nbt.putInt("tick_counter", tickCounter);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        ContainerHelper.loadAllItems(nbt, upgrades, registryLookup);
        tickCounter = nbt.getInt("tick_counter");
    }

    @Override
    public int getContainerSize() {
        return this.UPGRADE_SLOTS;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syndId, Inventory inventory) {
        return new UpgradeMenu(syndId, inventory, ContainerLevelAccess.create(level, worldPosition));
    }

    @Override
    protected Component getDefaultName() {
        return ContainerMenuHelper.GetTranslatableMenuName(EntityRadarBlock.LOCATION);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.upgrades;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> newUpgrades) {
        this.upgrades = newUpgrades;
    }
}
