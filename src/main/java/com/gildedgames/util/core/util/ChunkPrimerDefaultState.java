package com.gildedgames.util.core.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ChunkPrimerDefaultState extends ChunkPrimer
{

    private IBlockState defaultState;

    public ChunkPrimerDefaultState(IBlockState defaultState)
    {
        this.defaultState = defaultState;
    }

    public IBlockState getBlockState(int x, int y, int z)
    {
        IBlockState orig = super.getBlockState(x, y, z);

        if (orig == Blocks.STRUCTURE_VOID.getDefaultState())
        {
            return Blocks.AIR.getDefaultState();
        }

        if (orig != Blocks.AIR.getDefaultState())
        {
            return orig;
        }

        return this.defaultState;
    }

}
