package net.javahub.musc.data;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

public abstract class Data implements Config {

    private final Map<String, String> data = new LinkedHashMap<>();

    @NotNull
    abstract String getOverride(String key);

    public String getValue(String key) {
        return data.get(key).isEmpty() ? getOverride(key) : data.get(key);
    }

    public void updateConfig(TreeSet<String> entries) {
        for (String entry : entries) {
            data.putIfAbsent(entry, "");
            data.computeIfAbsent(entry, data::remove);
        } this.save();
    }

    @Override
    public String getDirectory() {
        return "musc";
    }
}
