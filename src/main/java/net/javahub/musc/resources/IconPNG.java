package net.javahub.musc.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static net.javahub.musc.resources.Template.ROOT;

class IconPNG implements Resource {

    @Override
    public void getResource() throws IOException {
        Path icon = Path.of("server-icon.png");
        if (Files.exists(icon)) {
            Files.copy(icon, Path.of(ROOT.toString(), "pack.png"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
