package com.github.FlyBird.MobCage.mixins;

import com.github.FlyBird.MobCage.BlockCage;
import com.github.FlyBird.MobCage.Blocks;
import com.github.FlyBird.MobCage.TileEntityCage;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.github.FlyBird.MobCage.HelloFML.nameMap;

@Mixin({ItemBlock.class})

public abstract class ItemBlockMixin {
    @Unique
    private final ItemBlock instance=(ItemBlock)(Object)this;


    @Inject(method = {"addInformation"}, at = {@At("HEAD")})
    public void addInformation(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot, CallbackInfo ci) {
        if(item_stack.getItemAsBlock().getBlock().blockID==Blocks.blockCage.blockID) {
            if (item_stack.isBlock()) {
                if (item_stack.getItemAsBlock().getBlock() instanceof BlockCage && item_stack.hasTagCompound()) {
                    NBTTagCompound tag = item_stack.getTagCompound();
                    if (tag.getBoolean("HasEntity")) {
                        String entityString = tag.getString("EntityString");
                        if (nameMap.containsKey(entityString)) {
                            entityString = nameMap.get(entityString);
                        }
                        info.add("\u00a79" + entityString);
                        info.add("Health: \u00a74" + tag.getFloat("EntityHealth"));
                    }
                }
            } else {
                info.add("Empty");
            }
        }
    }

    @Inject(method = {"onItemRightClick"}, at = {@At("HEAD")}, cancellable = true)
    public void onItemRightClick(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> cir) {
        System.out.printf("现在是："+player.worldObj);
        if (instance.getBlock() instanceof BlockCage) {
            RaycastCollision rc = player.getSelectedObject(partial_tick, false);
            ItemStack playerHeldItemStack = player.getHeldItemStack();
            if (rc != null) {
                if (rc.isEntity() ) {
                    EntityLivingBase entity = rc.getEntityHit().getAsEntityLivingBase();
                    if (entity.worldObj instanceof WorldServer && entity.isEntityAlive() && entity instanceof EntityAnimal) {
                        entity.setDead();
                        int x = (int) Math.round(entity.posX);
                        int y = (int) entity.posY;
                        int z = (int) Math.round(entity.posZ);

                        //find an opening to place the cage
                        if (!(entity.worldObj.getBlock(x, y, z) == null)) {
                            boolean found = false;

                            for (int x1 = x - 1; x1 <= x + 1; x1++) {
                                if (found) break;

                                for (int z1 = z - 1; z1 <= z + 1; z1++) {
                                    if (entity.worldObj.getBlock(x1, y, z1) == null) {
                                        found = true;
                                        x = x1;
                                        z = z1;
                                        break;
                                    }
                                }
                            }
                        }
                        entity.worldObj.setBlock(x, y, z, Blocks.blockCage.blockID);
                        TileEntityCage tile = (TileEntityCage) entity.worldObj.getBlockTileEntity(x, y, z);
                        tile.onPlaced(x, y, z, entity);
                        playerHeldItemStack.stackSize--;
                        cir.setReturnValue(true);
                    }
                    cir.setReturnValue(true);
                }else if(rc.isBlock()){
                    player.tryPlaceHeldItemAsBlock(rc, Block.getBlock(Blocks.blockCage.blockID));
                    cir.setReturnValue(true);
                }
            }

        }
    }
}
