package javahub.mod.musc.config;

import static javahub.mod.musc.Musc.MOD_ID;

import javahub.mod.musc.logging.MuscLogger;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class MuscMusicConfig {
    private static final String path = new String(FabricLoader
            .getInstance().getConfigDir().toString() + "/" + MOD_ID);
    public MuscMusicConfig() {
        MuscLogger.info(path);
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();
    }

}