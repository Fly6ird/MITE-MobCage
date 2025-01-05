package com.github.FlyBird.MobCage.mixins;


import com.github.FlyBird.MobCage.BlockCage;

import com.github.FlyBird.MobCage.api.EnumItemRenderType;
import com.github.FlyBird.MobCage.api.IRenderBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin({RenderItem.class})
public class RenderItemMixin {

    @Unique
    private final RenderItem instance=(RenderItem)(Object)this;

    //物品栏渲染
    @Inject(method = {"renderItemIntoGUI"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V")})
    private void renderItemIntoGUIMixin(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, CallbackInfo ci,@Local Block block) {
        ((IRenderBlock)(instance.itemRenderBlocks)).mobCage$setFlag(EnumItemRenderType.INVENTORY);
        ((IRenderBlock)(instance.itemRenderBlocks)).mobCage$setItemStack(par3ItemStack);
    }

    //凋落物
    @Inject(method = {"doRenderItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V")})
    private void renderItemIntoGUIMixin(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9, CallbackInfo ci, @Local Block block) {
        ((IRenderBlock)(instance.itemRenderBlocks)).mobCage$setFlag(EnumItemRenderType.ENTITY);
        ((IRenderBlock)(instance.itemRenderBlocks)).mobCage$setItemStack(par1EntityItem.getEntityItem());
    }
}
