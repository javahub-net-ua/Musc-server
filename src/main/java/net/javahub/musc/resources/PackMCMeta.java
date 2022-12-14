package net.javahub.musc.resources;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.javahub.musc.Musc.Config;
import static net.javahub.musc.resources.ResourcesProvider.GSON;
import static net.javahub.musc.resources.Template.ROOT;

class PackMCMeta implements Resource {

    @Override
    public void getResource() throws IOException {
        try (Writer writer = Files.newBufferedWriter(Path.of(ROOT.toString(), "pack.mcmeta"))) {
            JsonObject json = new JsonObject();
            JsonObject pack = new JsonObject();
            pack.addProperty("pack_format", Config.resources.packFormat);
            pack.addProperty("description", Config.resources.description);
            json.add("pack", pack);
            GSON.toJson(json, writer);
        }
    }
}
