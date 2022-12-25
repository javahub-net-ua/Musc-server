package net.javahub.musc.data;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.NotNull;

public class MobBindingsData extends Data implements Config {

    @Override
    public String getName() {
        return "mob_bindings";
    }

    @Override
    public String getDirectory() {
        return "musc";
    }

    @Override
    @NotNull
    String getOverride(String key) {
        return "minecraft:player:0.0";
    }
}
