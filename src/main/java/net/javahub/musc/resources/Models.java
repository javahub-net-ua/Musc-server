package net.javahub.musc.resources;

import com.google.gson.JsonObject;
import net.javahub.musc.records.RecordBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.javahub.musc.Musc.RECORDS;
import static net.javahub.musc.resources.ResourcesProvider.GSON;
import static net.javahub.musc.resources.Template.MODELS;

class Models implements Resource {

    @Override
    public void getResource() throws IOException {
        for (RecordBuilder.Record record: RECORDS) {
            try (Writer writer = Files.newBufferedWriter(
                    Path.of(MODELS.toString(), record.getModelFileName()))) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "item/generated");

                JsonObject texture = new JsonObject();
                texture.addProperty("layer0", "musc:item/" + record.getItemName());
                json.add("textures",texture);
                GSON.toJson(json, writer);
            }
        }
    }
}
