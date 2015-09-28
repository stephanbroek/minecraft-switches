package me.mneme.minecraft.switches;

/**
 * Created by stephan on 9/27/15.
 */

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Switches.MODID, version = Switches.VERSION)
public class Switches
{
    public static final String MODID = "switches";
    public static final String VERSION = "0.2.0";

    public final static Block stonePressureBlock = new PressureBlock("stone_pressure_block",Material.rock,CreativeTabs.tabBlock,2.0f,6.0f,Block.soundTypePiston, BlockPressurePlate.Sensitivity.mobs);
    public final static Block woodenPressureBlock = new PressureBlock("wooden_pressure_block",Material.rock,CreativeTabs.tabBlock,2.0f,6.0f,Block.soundTypeWood, BlockPressurePlate.Sensitivity.everything);

    @Mod.Instance
    public static Switches instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerBlock(stonePressureBlock,"stone_pressure_block").setHarvestLevel("pickaxe",1);
        GameRegistry.registerBlock(woodenPressureBlock,"wooden_pressure_block").setHarvestLevel("pickaxe",1);

        GameRegistry.addShapedRecipe(new ItemStack(stonePressureBlock),"P","R","S",'P',new ItemStack(Blocks.stone_pressure_plate),'R',new ItemStack(Items.redstone),'S',new ItemStack(Blocks.stone));
        GameRegistry.addShapedRecipe(new ItemStack(woodenPressureBlock),"P","R","S",'P',new ItemStack(Blocks.wooden_pressure_plate),'R',new ItemStack(Items.redstone),'S',new ItemStack(Blocks.stone));
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Switches());
    }


}
