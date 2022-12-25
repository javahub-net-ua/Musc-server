package net.javahub.musc.data;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.NotNull;

public class DiscIDsData extends Data implements Config {

    @Override
    public String getName() {
        return "disc_item_ids";
    }

    @Override
    public String getDirectory() {
        return "musc";
    }

    @Override
    @NotNull
    String getOverride(String key) {
        return key.toLowerCase()
                .replaceAll(" \\(.*?\\)", "")
                .replaceFirst(" - ", "@")
                .replaceAll("[\\p{Punct}&&[^@-]]", "")
                .replaceAll("[\\s-]", "_");
    }
}
