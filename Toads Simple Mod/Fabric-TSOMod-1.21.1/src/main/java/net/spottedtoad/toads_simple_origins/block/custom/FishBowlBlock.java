package net.spottedtoad.toads_simple_origins.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.spottedtoad.toads_simple_origins.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class FishBowlBlock extends Block {
    public FishBowlBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}