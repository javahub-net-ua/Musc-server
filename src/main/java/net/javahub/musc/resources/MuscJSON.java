package net.javahub.musc.resources;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.javahub.musc.Musc.RECORDS;
import static net.javahub.musc.resources.ResourcesProvider.GSON;
import static net.javahub.musc.resources.Template.ROOT;

public class MuscJSON implements Resource {

    @Override
    public void getResource() throws IOException {
        try (Writer writer = Files.newBufferedWriter(Path.of(ROOT.toString(), "musc.json"))) {
            GSON.toJson(RECORDS, writer);
        }
    }
}
