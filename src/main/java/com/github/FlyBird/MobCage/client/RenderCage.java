package com.github.FlyBird.MobCage.client;

import java.util.HashMap;

import com.github.FlyBird.MobCage.TileEntityCage;
import net.minecraft.Minecraft;
import net.minecraft.ItemRenderer;
import net.minecraft.Tessellator;
import net.minecraft.RenderManager;
import net.minecraft.TextureManager;
import net.minecraft.TileEntitySpecialRenderer;
import net.minecraft.EntityList;
import net.minecraft.EntityLiving;
import net.minecraft.NBTTagCompound;
import net.minecraft.TileEntity;
import net.minecraft.ResourceLocation;

import org.lwjgl.opengl.GL11;


public class RenderCage extends TileEntitySpecialRenderer
{
	public ResourceLocation texture;
	public TextureManager engine;
	public float defaultThickness = 0.0625F;
	public EntityLiving clientEntity;
	
	public static HashMap<String, EntityLiving> entities = new HashMap<String, EntityLiving>();
	
	public RenderCage()
	{
		texture = new ResourceLocation( "textures/items/textures.png");
	}

	public void renderFrame(double x, double y, double z, float var8, boolean forInv)
	{
		engine = Minecraft.getMinecraft().getTextureManager();
		GL11.glPushMatrix();
	    GL11.glTranslated(x, y, z);
	    engine.bindTexture(texture);

	    GL11.glRotatef(90F, 1F, 0F, 0F);
	    render3DTexture(1);
	    GL11.glRotatef(-90F, 1F, 0F, 0F);
	    GL11.glTranslatef(0, 0, 0.0625F);
	    if (!forInv) {
	    	render3DTexture(0, 0.0625F / 2);
	    }
	    GL11.glTranslatef(0, 0, 1 - 0.0625F);
	    GL11.glTranslatef(0, 0, -0.0625F / 2);
	    render3DTexture(0, 0.0625F / 2);
	    GL11.glTranslatef(0, 0, (0.0625F / 2));
	    GL11.glRotatef(90F, 0F, 1F, 0F);
	    
	    GL11.glTranslatef(0, 0, 0.0625F);
	    if (!forInv) {
	    	render3DTexture(0, 0.0625F / 2);
	    }
	    render3DTexture(2);
	    GL11.glTranslatef(0, 0, 1 - 0.0625F);
	    render3DTexture(2);
	    GL11.glTranslatef(0, 0, 0.0625F / -2);
	    render3DTexture(0, 0.0625F / 2);
	    GL11.glTranslatef(0, 1 - 0.0625F, -1 - (-0.0625F / 2));
	    GL11.glRotatef(90F, 1F, 0F, 0F);
	    render3DTexture(1);
	    GL11.glRotatef(-90F, 1F, 0F, 0F);
	    GL11.glPopMatrix();
	}
	
	public void renderEntity(EntityLiving entity, double x, double y, double z)
	{
		if (entity != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef(1F, 0F, 0.9F);
			RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);
			GL11.glPopMatrix();
		}
	}
	
	public void render3DTexture(int index)
	{
		render3DTexture(index, 0.0625F);
	}
	
	public void render3DTexture(int index, float thickness)
	{
		Tessellator tess = Tessellator.instance;
        float var9 = (float)(index % 16 * 16 + 0) / 256.0F;
        float var10 = (float)(index % 16 * 16 + 16) / 256.0F;
        float var11 = (float)(index / 16 * 16 + 0) / 256.0F;
        float var12 = (float)(index / 16 * 16 + 16) / 256.0F;
        ItemRenderer.renderItemIn2D(tess, var10, var11, var9, var12, 256, 256, thickness); 
	}
	
	public EntityLiving getEntity(String entityID, NBTTagCompound entityData)
	{
		if (!entities.containsKey(entityID)) {
			EntityLiving entity = (EntityLiving)EntityList.createEntityByName(entityID, Minecraft.getMinecraft().theWorld);
			entities.put(entityID, entity);
		}
		EntityLiving entity = entities.get(entityID);
		if (entityData != null && entity != null) {
			entity.readEntityFromNBT(entityData);
		}
		return entity;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float var8) 
	{
		TileEntityCage cage = (TileEntityCage)tile;
		renderFrame(x, y, z, var8, false);
		if (cage.hasEntity) {
			EntityLiving entity = getEntity(cage.entityID, cage.entityData);
			renderEntity(entity, x, y, z);
		}
	}
}