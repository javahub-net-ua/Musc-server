package net.javahub.mod.musc.list;

import static net.javahub.mod.musc.Musc.CONFIG;
import static net.javahub.mod.musc.Musc.MOD_ID;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MuscSetList {

    private static String path;

    public MuscSetList() {
        path = CONFIG.getConfigDirectory() + "/" + MOD_ID;
    }

    public Path getMusicDirectory() {
        return Paths.get(path + "/");
    }

    public FileChannel getZIP() {
        try {




            return FileChannel.open(Paths.get(path + ".zip"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}