package me.mneme.minecraft.switches;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by stephan on 9/28/15.
 */
public class PressureBlock extends MultiTextureBlock
{
    private BlockPressurePlate.Sensitivity sensitivity;
    private int tickCount; // to be NBT (max 10) 4 bit
    private boolean active = false; // 1 bit

    // total 10 bits

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
        return 5;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        notifyUpdate(world, x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6)
    {
        notifyUpdate(world, x, y, z);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess par1, int x, int y, int z, int par5)
    {
        return (active) ? 15 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess par1, int x, int y, int z, int par5)
    {
        return (active) ? 1 : 0;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    protected AxisAlignedBB getAABB(int x, int y, int z)
    {
        return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x+1), (double)(y+1), (double)(z+1));
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if(!world.isRemote && !entity.doesEntityNotTriggerPressurePlate())
        {
            switch (sensitivity)
            {
                case everything:
                    triggered(world, x, y, z);
                    break;
                case mobs:
                    if(entity instanceof EntityLivingBase)
                    {
                        triggered(world, x, y, z);
                    }
                    break;
                case players:
                    if(entity instanceof EntityPlayer)
                    {
                        triggered(world, x, y, z);
                    }
                    break;
            }
        }
    }

    private void triggered(World world, int x, int y, int z)
    {
        if(!active)
        {
            active = true;
            notifyUpdate(world, x, y, z);
        }
        tickCount = 0;
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        System.out.println(x + "," + y + "," + z + "," + tickCount);
    }

    public void updateTick(World world, int x, int y, int z, Random rnd)
    {
        if (tickCount < 1)
        {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        } else {
            active = false;
            notifyUpdate(world, x, y, z);
        }
        tickCount++;
    }

    private void notifyUpdate(World world, int x, int y, int z)
    {
        world.notifyBlockOfNeighborChange(x - 1, y, z, this);
        world.notifyBlockOfNeighborChange(x + 1, y, z, this);
        world.notifyBlockOfNeighborChange(x, y - 1, z, this);
        world.notifyBlockOfNeighborChange(x, y + 1, z, this);
        world.notifyBlockOfNeighborChange(x, y, z - 1, this);
        world.notifyBlockOfNeighborChange(x, y, z + 1, this);
    }

}
