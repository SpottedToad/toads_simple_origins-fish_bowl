package net.spottedtoad.toads_simple_origins.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class EmptyFishBowlBlockEntity extends BlockEntity {


    public EmptyFishBowlBlockEntity(BlockPos pos, BlockState state) {
        //Implements block entity
        super(ModBlockEntities.EMPTY_FISH_BOWL_BLOCK_ENTITY, pos, state);

    }
    public Packet<ClientPlayPacketListener> getUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return this.createNbt(registries);
    }




    //Create damage tracking field
    private int savedDamage = 0;

    //Allow item-block durability data transfer
    public int getSavedDamage() { return this.savedDamage; }
    public void setSavedDamage(int damage) {
        this.savedDamage = damage;
        this.markDirty(); }

    //Save durability data
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putInt("savedDamage", this.savedDamage);
    }
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("savedDamage")) {
            this.savedDamage = nbt.getInt("savedDamage");
        }
    }
}
