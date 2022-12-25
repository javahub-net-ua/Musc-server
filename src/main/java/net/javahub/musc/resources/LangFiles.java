package net.javahub.musc.resources;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static net.javahub.musc.Musc.Config;
import static net.javahub.musc.Musc.RECORDS;
import static net.javahub.musc.resources.ResourcesProvider.GSON;
import static net.javahub.musc.resources.Template.LANG;

class LangFiles implements Resource {

    @Override
    public void getResource() throws IOException {
        for (Map.Entry<String, String> translation : Config.resources.localizations.entrySet()) {
            JsonObject json = new JsonObject();
            RECORDS.forEach(record -> {
                json.addProperty(String.format("item.musc.%s", record.getItemName()), translation.getValue());
                json.addProperty(String.format("item.musc.%s.desc", record.getItemName()), record.getTitle());
            });
            try (Writer writer = Files.newBufferedWriter(Path.of(LANG.toString(), translation.getKey() + ".json"))) {
                GSON.toJson(json, writer);
            }
        }
    }
}
