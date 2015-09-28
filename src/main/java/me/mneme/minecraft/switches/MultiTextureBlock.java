package me.mneme.minecraft.switches;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

/**
 * Created by stephan on 9/27/15.
 */
public class MultiTextureBlock extends QuickBlock
{

    protected MultiTextureBlock(String unlocalizedName, Material material, CreativeTabs creativeTab, Float hardness, Float resistance, SoundType soundType)
    {
        super(unlocalizedName, material, creativeTab, hardness, resistance, soundType);
    }

    public IIcon[] icons = new IIcon[6];

    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
        for(int i=0; i<6; i++)
        {
            this.icons[i] = reg.registerIcon(this.textureName + "_" + i);
        }
    }

    @Override
    public  IIcon getIcon(int side, int meta)
    {
        return this.icons[side];
    }
}
