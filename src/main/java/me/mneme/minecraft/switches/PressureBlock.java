package me.mneme.minecraft.switches;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by stephan on 9/28/15.
 */
public class PressureBlock extends MultiTextureBlock
{
    private BlockPressurePlate.Sensitivity sensitivity;
    private int tickCount;
    private boolean active = false;
    private boolean switched = false;

    protected PressureBlock(String unlocalizedName, Material material, CreativeTabs creativeTab, Float hardness, Float resistance, SoundType soundType, BlockPressurePlate.Sensitivity sensitivity)
    {
        super(unlocalizedName, material, creativeTab, hardness, resistance, soundType);
        this.sensitivity = sensitivity;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0f,0.0f,0.0f,1.0f,0.998f,1.0f);
    }

    public int tickRate(World world)
    {
        return 1;
    }

    @Override
    public void onBlockAdded(World world, int par2, int par3, int par4)
    {
        notifyUpdate(world, par2, par3, par4);
    }

    @Override
    public void breakBlock(World world, int par2, int par3, int par4, Block par5, int par6)
    {
        notifyUpdate(world, par2, par3, par4);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess par1, int par2, int par3, int par4, int par5)
    {
        return (active) ? 15 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess par1, int par2, int par3, int par4, int par5)
    {
        return (active) ? 1 : 0;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int par2, int par3, int par4, Entity entity)
    {
        if(!world.isRemote && !entity.doesEntityNotTriggerPressurePlate())
        {
            switch (sensitivity)
            {
                case everything:
                    triggered(world, par2, par3, par4);
                    break;
                case mobs:
                    if(entity instanceof EntityCreature || entity instanceof EntityPlayer)
                    {
                        triggered(world, par2, par3, par4);
                    }
                    break;
                case players:
                    if(entity instanceof EntityPlayer)
                    {
                        triggered(world, par2, par3, par4);
                    }
                    break;
            }
        }
    }

    private void triggered(World world, int par2, int par3, int par4)
    {
        tickCount = 0;
        world.scheduleBlockUpdate(par2, par3, par4, this, 1);
        System.out.println(par2 + "," + par3 + "," + par4 + tickCount);
        if(!active)
        {
            active = true;
            switched = true;
        }
    }

    public void updateTick(World world, int par2, int par3, int par4, Random par5)
    {
        if (switched)
        {
            notifyUpdate(world, par2, par3, par4);
        }
        tickCount++;
        if (tickCount<40)
        {
            world.scheduleBlockUpdate(par2, par3, par4, this, 1);
        } else {
            active = false;
            notifyUpdate(world, par2, par3, par4);
        }
    }

    private void notifyUpdate(World world, int par2, int par3, int par4)
    {
        world.notifyBlockOfNeighborChange(par2 - 1, par3, par4, this);
        world.notifyBlockOfNeighborChange(par2 + 1, par3, par4, this);
        world.notifyBlockOfNeighborChange(par2, par3 - 1, par4, this);
        world.notifyBlockOfNeighborChange(par2, par3 + 1, par4, this);
        world.notifyBlockOfNeighborChange(par2, par3, par4 - 1, this);
        world.notifyBlockOfNeighborChange(par2, par3, par4 + 1, this);
    }

}
