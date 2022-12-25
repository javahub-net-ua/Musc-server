package net.javahub.musc.data;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.NotNull;

public class DiscColorsData extends Data implements Config {

    @Override
    @NotNull
    String getOverride(String key) {
        return Integer.toHexString(Math.abs(key.hashCode() *
                new StringBuilder(key).reverse().toString().hashCode() / 0xFF));
    }

    @Override
    public String getName() {
        return "disc_colors";
    }
}
