package net.javahub.musc.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.javahub.musc.records.Record;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.javahub.musc.Musc.RECORDS;
import static net.javahub.musc.resources.ResourcesProvider.GSON;
import static net.javahub.musc.resources.Template.ASSETS;

class SoundsFile implements Resource {

    @Override
    public void getResource() throws IOException {
        JsonObject json = new JsonObject();
        for (Record record : RECORDS) {
            JsonArray sounds = new JsonArray();
            JsonObject sound = new JsonObject();
            sound.addProperty("name", "musc:records/" + record.getSoundName());
            sound.addProperty("stream", "true");
            sounds.add(sound);

            JsonObject info = new JsonObject();
            info.addProperty("category", "record");
            info.add("sounds", sounds);

            json.add("music_disc." + record.getSoundName(), info);
        }
        try (Writer writer = Files.newBufferedWriter(Path.of(ASSETS.toString(), "sounds.json"))) {
            GSON.toJson(json, writer);
        }
    }
}
