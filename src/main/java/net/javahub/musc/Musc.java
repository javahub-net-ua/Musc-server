package net.javahub.musc;

import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.javahub.musc.config.MuscConfig;
import net.javahub.musc.discs.MuscItems;
import net.javahub.musc.lootTables.MuscLootTableProvider;
import net.javahub.musc.records.RecordBuilder;
import net.javahub.musc.records.RecordUtils;
import net.javahub.musc.resources.ResourcesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.LinkedHashSet;

public class Musc implements ModInitializer {

    public static final MuscConfig CONFIG = OmegaConfig.register(MuscConfig.class);
    public static final Logger LOGGER = LoggerFactory.getLogger(CONFIG.getName());
    public static final LinkedHashSet<RecordBuilder.Record> RECORDS = RecordUtils.getRecords();
    public static final Path RESOURCES = ResourcesProvider.getResources();

    @Override
    public void onInitialize() {
        RECORDS.forEach(MuscItems::registerRecord);
        RECORDS.forEach(MuscLootTableProvider::modifyLootTables);
    }
}
