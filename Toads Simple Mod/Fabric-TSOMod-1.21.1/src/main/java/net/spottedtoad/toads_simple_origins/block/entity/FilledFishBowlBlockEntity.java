package net.spottedtoad.toads_simple_origins.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class FilledFishBowlBlockEntity extends BlockEntity {
    public final FilledFishBowlBlockEntityAnimationDispatcher dispatcher;

    public FilledFishBowlBlockEntity(BlockPos pos, BlockState state) {
        //Implements block entity
        super(ModBlockEntities.FILLED_FISH_BOWL_BLOCK_ENTITY, pos, state);
        this.dispatcher = new FilledFishBowlBlockEntityAnimationDispatcher(this);
    }

    public Packet<ClientPlayPacketListener> getUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
