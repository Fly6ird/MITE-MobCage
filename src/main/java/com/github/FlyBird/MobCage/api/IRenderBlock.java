package com.github.FlyBird.MobCage.api;

import net.minecraft.ItemStack;

public interface IRenderBlock {
    void mobCage$setFlag(EnumItemRenderType type);
    EnumItemRenderType mobCage$getFlag();
    void mobCage$setItemStack(ItemStack itemStack);
}
