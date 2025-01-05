package com.github.FlyBird.MobCage;

import net.minecraft.*;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;


public class Items extends Item {
    protected Items(int id, Material material, String texture) {
        super(id,material,texture);
    }

   // public static final ItemCage itemCage =new ItemCage(getNextItemID(),Material.wood,"empty");
    public static final ItemCrowbar crowBar =new ItemCrowbar(getNextItemID(),Material.wood,"crowbar");

    public static void registerItems(ItemRegistryEvent event) {
        event.register("MobCage","crowbar", crowBar);
    }

    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(crowBar,1),true,new Object[] {"X X", "XXX", " X ", 'X',new ItemStack(Item.chainIron,1)});
       // register.registerShapedRecipe(new ItemStack(itemCage, 1), true, new Object[] {"XXX", "YYY", "XXX",Character.valueOf('X'),new ItemStack((Block.planks),1,1),Character.valueOf('Y'),new ItemStack((Block.fenceIron),1,1)});
    }


    public static int getNextItemID() {
        return IdUtil.getNextItemID();
    }

}
