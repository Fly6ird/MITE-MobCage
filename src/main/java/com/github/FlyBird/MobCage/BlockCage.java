package com.github.FlyBird.MobCage;

import java.util.Random;
import net.minecraft.*;

import static net.xiaoyu233.fml.reload.utils.IdUtil.getNextRenderType;

public class BlockCage extends BlockContainer
{

	public static int blockCageRenderType=getNextRenderType();

	public BlockCage(int blockID, Material Material, BlockConstants constants)
	{
		super(blockID, Material, constants);
		this.setHardness(0.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundWoodFootstep);
		this.setUnlocalizedName("cage");
		this.setTextureName("empty");
		this.setResistance(30F);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float dx, float dy, float dz) {
		TileEntityCage tile = (TileEntityCage)world.getBlockTileEntity(x, y, z);
		if (tile == null || !tile.hasEntity) {
			return false;
		}

		ItemStack heldItemStack = player.getHeldItemStack();
		if (heldItemStack == null) {
			return false;
		}
		Item heldItem = heldItemStack.getItem();

		if (heldItem instanceof ItemCrowbar) {
			((ItemCrowbar)heldItem).usedOnCage(heldItemStack, player, world, x, y, z);
			if (player.onServer())
				return tile.releaseEntity(x, y, z) ;
			return true;
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int blockID, int fortune) {
		TileEntityCage tile = (TileEntityCage) world.getBlockTileEntity(x, y, z);
		//这个metadata值是为了解决setblock会触发breakblock而作的标记
		if (tile != null && world instanceof WorldServer&&world.getBlockMetadata(x,y,z)!=15) {
			tile.handleBreaking(x, y, z); // 处理笼子内生物的逻辑
		}
		super.breakBlock(world, x, y, z, blockID, fortune);
	}

	public boolean onBlockPlacedMITE(World world, int x, int y, int z, int metadata, Entity placer, boolean test_only) {
		if (!test_only && placer instanceof EntityLivingBase) {
			ItemStack item_stack = placer.getAsEntityLivingBase().getHeldItemStack();
			if (item_stack.hasDisplayName()) {
				TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
				if (tile_entity != null) {
					tile_entity.setCustomInvName(item_stack.getDisplayName());
				}
			}
			if(item_stack.hasTagCompound()) {
				TileEntityCage tile = (TileEntityCage) world.getBlockTileEntity(x, y, z);
				if (tile != null) {
					tile.entityID = item_stack.getTagCompound().getString("EntityString");
					tile.hasEntity = item_stack.getTagCompound().getBoolean("HasEntity");
					tile.entityHealth = item_stack.getTagCompound().getFloat("EntityHealth");
					tile.entityData = item_stack.getTagCompound().getCompoundTag("EntityData");
					world.markBlockForUpdate(x, y, z);
				}
			}
		}

		return true;
	}


	@Override
	public int getMetadataForPlacement(World world, int x, int y, int z, ItemStack item_stack, Entity entity, EnumFace face, float offset_x, float offset_y, float offset_z) {
		//int metadata = super.getMetadataForPlacement(world, x, y, z, item_stack, entity, face, offset_x, offset_y, offset_z);
		if (item_stack.hasTagCompound()) {
			TileEntityCage tile = (TileEntityCage) entity.getWorld().getBlockTileEntity(x, y, z);
			if (tile != null) {
				tile.entityID = item_stack.getTagCompound().getString("EntityString");
				tile.hasEntity = item_stack.getTagCompound().getBoolean("HasEntity");
				tile.entityHealth = item_stack.getTagCompound().getFloat("EntityHealth");
				tile.entityData = item_stack.getTagCompound().getCompoundTag("EntityData");
				entity.getWorld().markBlockForUpdate(x, y, z);
			}
		}
		return super.getMetadataForPlacement(world, x, y, z, item_stack, entity, face, offset_x, offset_y, offset_z);
	}
	@Override
	public boolean isAlwaysOpaqueStandardFormCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int dropBlockAsEntityItem(BlockBreakInfo info) {
		return 0;
	}


    @Override
    public int getRenderType()
    {
        return blockCageRenderType;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCage();
	}
}
