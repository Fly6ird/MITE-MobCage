package com.github.FlyBird.MobCage.mixins;

import com.github.FlyBird.MobCage.BlockCage;
import com.github.FlyBird.MobCage.TileEntityCage;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityHorse.class})
public class EntityHorseMixin extends EntityLiving {
    @Unique
    private final EntityHorse instance=(EntityHorse)(Object)this;

    public EntityHorseMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = {"onEntityRightClicked "}, at = {@At("HEAD")}, cancellable = true)
    private void onEntityRightClicked (EntityPlayer player, ItemStack item_stack, CallbackInfoReturnable<Boolean> cir) {
        if(item_stack != null)
        {
            if(item_stack.getItem().getAsItemBlock().getBlock() instanceof BlockCage)
            {
                cir.setReturnValue(super.onEntityRightClicked(player,item_stack));
            }
        }
    }

}
