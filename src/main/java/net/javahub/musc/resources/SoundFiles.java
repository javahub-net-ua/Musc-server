package net.javahub.musc.resources;

import net.javahub.musc.records.Record;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static net.javahub.musc.Musc.RECORDS;
import static net.javahub.musc.resources.Template.SOUNDS;

class SoundFiles implements Resource {

    @Override
    public void getResource() throws IOException {
        for (Record record : RECORDS) {
            Files.copy(record.getPath(),
                    Path.of(SOUNDS.toString(), record.getSoundFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
