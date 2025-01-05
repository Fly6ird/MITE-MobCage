package com.github.FlyBird.MobCage.mixins;


import com.github.FlyBird.MobCage.BlockCage;
import com.github.FlyBird.MobCage.api.EnumItemRenderType;
import com.github.FlyBird.MobCage.api.IRenderBlock;
import com.github.FlyBird.MobCage.client.RenderCage;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({RenderBlocks.class})
public class RenderBlocksMixin implements IRenderBlock {
  @Unique
  private  EnumItemRenderType renderItemType;  //0为物品栏  1为手持  2为凋落物

  @Unique
  private ItemStack itemStack;

  @Unique
  RenderCage cage = new RenderCage();

  @Inject(method = {"renderItemIn3d"}, at = {@At("HEAD")}, cancellable = true)
  private static void register(int renderType, CallbackInfoReturnable<Boolean> cir) {
    if (renderType == BlockCage.blockCageRenderType)
      cir.setReturnValue(Boolean.TRUE);
  }
  
  @Inject(method = {"renderBlockAsItem"}, at = {@At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/Block;getRenderType()I")})
  private void register(Block par1Block, int par2, float par3, CallbackInfo ci) {
    int renderType = par1Block.getRenderType();

    if (renderType == BlockCage.blockCageRenderType) {
      if(renderItemType==EnumItemRenderType.INVENTORY) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0F, -0.1F, 0F);
        //GL11.glEnable(GL11.GL_LIGHTING);
        cage.renderFrame(0, 0, 0, 0, true);
        GL11.glTranslatef(0F, 0.1F, 0F);
      }
      if(renderItemType==EnumItemRenderType.ENTITY){
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef(-0.5F, -0.4F, -0.5F);
        cage.renderFrame(0, 0, 0, 0, false);
      }
      if(renderItemType==EnumItemRenderType.HAND)
      {
        GL11.glTranslatef(1F, 0.2F, 1F);
        GL11.glRotatef(180F, 0F, 1F, 0F);
        cage.renderFrame(0, 0, 0, 0, false);
      }
      if (itemStack.hasTagCompound()) {
        NBTTagCompound tag = itemStack.getTagCompound();
        EntityLiving entity = cage.getEntity(tag.getString("EntityString"), tag.getCompoundTag("EntityData"));
        cage.renderEntity(entity, 0, 0, 0);
      }
    }
  }

  @Override
  public void mobCage$setFlag(EnumItemRenderType type) {
    renderItemType =type;
  }

  @Override
  public EnumItemRenderType mobCage$getFlag() {
    return renderItemType;
  }

  @Override
  public void mobCage$setItemStack(ItemStack itemStack1) {
    itemStack=itemStack1;
  }
}