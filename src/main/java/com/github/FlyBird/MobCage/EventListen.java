package com.github.FlyBird.MobCage;

import com.github.FlyBird.MobCage.client.RenderCage;
import com.google.common.eventbus.Subscribe;
import net.xiaoyu233.fml.reload.event.*;

public class EventListen {

    @Subscribe
    public void onItemRegister(ItemRegistryEvent event) {
        //物品被注册事件
        Blocks.registerItemBlocks(event);
        Items.registerItems(event);
    }

    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event) {
        //合成方式被注册事件
        Blocks.registerRecipes(event);
        Items.registerRecipes(event);
    }

    //玩家登录事件
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {

    }

    @Subscribe
    public void onTileEntityRegister(TileEntityRegisterEvent event) {
        event.register(TileEntityCage.class, "Cage");
    }

    @Subscribe
    public void onTileEntityRendererRegister(TileEntityRendererRegisterEvent event) {
        event.register(TileEntityCage.class, new RenderCage());
    }
}
