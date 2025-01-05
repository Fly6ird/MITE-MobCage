package com.github.FlyBird.MobCage.mixins;

import com.github.FlyBird.MobCage.TileEntityCage;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetClientHandler.class})
public abstract class NetClientHandlerMixin extends NetHandler  {
    @Shadow
    private WorldClient worldClient;

    @Shadow
    private Minecraft mc;

    @Inject(method = {"handleTileEntityData"}, at = {@At("HEAD")})
    private void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData, CallbackInfo ci) {
        if (this.mc.theWorld.blockExists(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition)) {
            TileEntity tileEntity = this.mc.theWorld.getBlockTileEntity(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition);
            if (tileEntity != null) {
                if (par1Packet132TileEntityData.actionType == 1 && tileEntity instanceof TileEntityCage) {
                    tileEntity.readFromNBT(par1Packet132TileEntityData.data);
                }
            }
        }
    }
}