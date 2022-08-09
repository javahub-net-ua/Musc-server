package javahub.mod.musc.config;

import static javahub.mod.musc.Musc.MOD_ID;

import javahub.mod.musc.logging.MuscLogger;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
public class MuscMusicConfig {
    private Path path = Paths.get(FabricLoader
            .getInstance().getConfigDir().toString(), MOD_ID);
    public MuscMusicConfig() {
        MuscLogger.info(path.toString());
    }

}