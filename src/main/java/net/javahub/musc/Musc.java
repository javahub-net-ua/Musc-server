package net.javahub.musc;

import io.wispforest.owo.Owo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.javahub.musc.config.MuscConfig;
import net.javahub.musc.discs.MuscItems;
import net.javahub.musc.logging.MuscLogger;
import net.javahub.musc.records.Record;
import net.javahub.musc.records.RecordUtils;
import net.javahub.musc.resources.Resources;
import net.minecraft.item.Item;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Musc implements ModInitializer {

    public static final String MOD_ID = "musc";
    public static final MuscConfig CONFIG = MuscConfig.createAndLoad();
    public static final MuscLogger LOGGER = new MuscLogger();
    public static final Set<Record> RECORDS = RecordUtils.getRecords();
    public static final Path RESOURCES = new Resources().of(RECORDS);
    public static final Map<Record, Item> ITEMS = new LinkedHashMap<>();

    @Override
    public void onInitialize() {
        RECORDS.forEach(MuscItems::registerRecord);
    }
}
