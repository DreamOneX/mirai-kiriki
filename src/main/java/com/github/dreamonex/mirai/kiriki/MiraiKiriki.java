package com.github.dreamonex.mirai.kiriki;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;

public final class MiraiKiriki extends JavaPlugin {
    public static final MiraiKiriki INSTANCE = new MiraiKiriki();

    private MiraiKiriki() {
        super(new JvmPluginDescriptionBuilder("com.github.dreamonex.mirai.kiriki", "0.0.1")
                .name("Mirai Kiriki")
                .info("KDE 小游戏 Kiriki 的 mirai 实现")
                .author("DreamOneX")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
    }
}