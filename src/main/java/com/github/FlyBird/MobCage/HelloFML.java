package com.github.FlyBird.MobCage;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.reload.event.MITEEvents;

import java.util.HashMap;

public class HelloFML implements ModInitializer {
    public static HashMap<String, String> nameMap = new HashMap<String, String>();
    static
    {
        nameMap.put("EntityHorse", "Horse");
        nameMap.put("Ozelot", "Ocelot");
        nameMap.put("MushroomCow", "Mooshroom");
    }

    @Override
    public void onInitialize() {   //相当于main函数，万物起源
        MITEEvents.MITE_EVENT_BUS.register(new EventListen());//注册一个事件监听类
    }
}