package com.github.dreamonex.mirai.kiriki.config;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;

public class Messages extends JavaAutoSavePluginConfig {
    public static final Messages INSTANCE = new Messages();

    public final Value<String> Hello = value("Hello", "Hello, kiriki!");

    private Messages() {
        super("Messages");
    }
}
