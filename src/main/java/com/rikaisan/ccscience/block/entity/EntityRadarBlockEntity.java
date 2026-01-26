package com.rikaisan.ccscience.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rikaisan.ccscience.peripheral.EntityRadarPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityRadarBlockEntity extends BlockEntity {
    private static final Codec<List<ScanEntry>> SCAN_CODEC = Codec.list(ScanEntry.CODEC);
    private final EntityRadarPeripheral peripheral = new EntityRadarPeripheral(this);
    private List<ScanEntry> lastScan = List.of();
    private int sweepCounter = 0;

    public EntityRadarBlockEntity(BlockPos pos, BlockState state) {
        super(CCScienceBlockEntityType.ENTITY_RADAR, pos, state);
    }

    private List<Entity> getEntitiesInRadius(Level world, int radius) {
        if (radius < 0)
            throw new IllegalArgumentException("Radius may not be negative");
        final Vec3i boxRadius = new Vec3i(radius, radius, radius);
        return world.getEntities(null, AABB.encapsulatingFullBlocks(this.getBlockPos().subtract(boxRadius), this.getBlockPos().offset(boxRadius)));
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, EntityRadarBlockEntity entity) {
        entity.tick(world, blockPos, blockState);
    }

    public void tick(Level world, BlockPos blockPos, BlockState blockState) {
        sweepCounter++;

        setChanged();

        if (!peripheral.isAttached() || sweepCounter < 20)
            return;

        sweep(world, blockPos, blockState);
        sweepCounter = 0;
    }

    public void sweep(Level world, BlockPos blockPos, BlockState blockState) {
        final Vec3 origin = Vec3.atCenterOf(blockPos);

        lastScan = getEntitiesInRadius(world, 8).stream().map(entity -> {
            final Vec3 relativePos = entity.position().subtract(origin);
            return new ScanEntry(
                        entity.getDisplayName().getString(),
                        EntityType.getKey(entity.getType()),
                        relativePos
                    );
        }).toList();

        peripheral.updateScanData(lastScan);
    }
    public List<ScanEntry> getLastScan() {
        return lastScan;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
        nbt.putInt("sweep_counter", sweepCounter);
        SCAN_CODEC.encodeStart(NbtOps.INSTANCE, lastScan).ifSuccess(
                tag -> nbt.put("last_scan", tag)
        );
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, Provider registryLookup) {
        sweepCounter = nbt.getInt("sweep_counter");
        SCAN_CODEC.parse(NbtOps.INSTANCE, nbt.get("last_scan"))
                .ifSuccess(scan -> lastScan = scan);
    }

    public IPeripheral peripheral() {
        return peripheral;
    }

    public record ScanEntry(String name, ResourceLocation type, Vec3 pos) {
        public static final Codec<ScanEntry> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("name").forGetter(ScanEntry::name),
                    ResourceLocation.CODEC.fieldOf("type").forGetter(ScanEntry::type),
                    Vec3.CODEC.fieldOf("pos").forGetter(ScanEntry::pos)
            ).apply(instance, ScanEntry::new)
        );
    }
}
