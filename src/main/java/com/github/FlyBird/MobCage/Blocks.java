package com.github.FlyBird.MobCage;

import net.minecraft.Block;
import net.minecraft.BlockConstants;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class Blocks extends Block {
    protected Blocks(int blockID, Material Material, BlockConstants constants) {
        super(blockID, Material, constants);
    }

    public static final Block blockCage =new BlockCage(getNextBlockID(),Material.stone, new BlockConstants().setNotAlwaysLegal().setNeverHidesAdjacentFaces());

    //注册方块的物品事件
    public static void registerItemBlocks(ItemRegistryEvent registryEvent) {
        registryEvent.registerItemBlock("MobCage", "empty", blockCage);
    }

    //注册方块的合成表事件
    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(blockCage, 1), true, new Object[] {"XXX", "YYY", "XXX",Character.valueOf('X'),new ItemStack((Block.planks),1,0),Character.valueOf('Y'),new ItemStack((Block.fenceIron),1,1)});
        register.registerShapedRecipe(new ItemStack(blockCage, 1), true, new Object[] {"XXX", "YYY", "XXX",Character.valueOf('X'),new ItemStack((Block.planks),1,1),Character.valueOf('Y'),new ItemStack((Block.fenceIron),1,1)});
        register.registerShapedRecipe(new ItemStack(blockCage, 1), true, new Object[] {"XXX", "YYY", "XXX",Character.valueOf('X'),new ItemStack((Block.planks),1,2),Character.valueOf('Y'),new ItemStack((Block.fenceIron),1,1)});
        register.registerShapedRecipe(new ItemStack(blockCage, 1), true, new Object[] {"XXX", "YYY", "XXX",Character.valueOf('X'),new ItemStack((Block.planks),1,3),Character.valueOf('Y'),new ItemStack((Block.fenceIron),1,1)});
    }

    public static int getNextBlockID() {return IdUtil.getNextBlockID();}
}