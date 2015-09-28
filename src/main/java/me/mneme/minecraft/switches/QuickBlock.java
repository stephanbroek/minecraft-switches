package me.mneme.minecraft.switches;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by stephan on 9/27/15.
 */
public class QuickBlock extends Block
{

    protected QuickBlock(String unlocalizedName, Material material, CreativeTabs creativeTab, Float hardness, Float resistance, SoundType soundType)
    {
        super(material);
        this.setBlockName(unlocalizedName);
        this.setBlockTextureName(Switches.MODID + ":" + unlocalizedName);
        this.setCreativeTab(creativeTab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setStepSound(soundType);
    }
}
