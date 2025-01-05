package com.github.FlyBird.MobCage;

import net.minecraft.*;

public class ItemCrowbar extends Item implements IDamageableItem
{
	public ItemCrowbar(int id, Material material, String texture)
	{
		super(id,material,texture);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("crowbar");
		this.setTextureName( "crowbar");
		this.setFull3D();
		this.setMaxDamage(10);
		this.setMaxStackSize(1);
	}
	
	public void usedOnCage(ItemStack stack, EntityPlayer player, World world, int x, int y, int z)
	{
		if(!world.isRemote) {
			player.tryDamageHeldItem(DamageSource.generic, 1);

		}
		else
			player.swingArm();//player.swingItem();
	}

	@Override
	public int getNumComponentsForDurability() {
		return 0;
	}

	@Override
	public int getRepairCost() {
		return 0;
	}
}
