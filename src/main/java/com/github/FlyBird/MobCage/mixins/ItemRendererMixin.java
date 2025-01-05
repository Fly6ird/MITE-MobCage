package com.github.FlyBird.MobCage.mixins;

import com.github.FlyBird.MobCage.BlockCage;

import com.github.FlyBird.MobCage.api.EnumItemRenderType;
import com.github.FlyBird.MobCage.api.IRenderBlock;
import com.github.FlyBird.MobCage.client.RenderCage;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public class ItemRendererMixin {
    @Unique
    private final ItemRenderer instance=(ItemRenderer)(Object)this;

    @Unique
    RenderCage cage = new RenderCage();

    //手持
    @Inject(method = {"renderItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V")})
    private void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3, CallbackInfo ci) {
        ((IRenderBlock)(instance.renderBlocksInstance)).mobCage$setFlag(EnumItemRenderType.HAND);
        ((IRenderBlock)(instance.renderBlocksInstance)).mobCage$setItemStack(par2ItemStack);

    }
}
