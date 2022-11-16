package net.javahub.musc.resources;

import net.javahub.musc.Musc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static net.javahub.musc.resources.Template.ROOT;

class IconPNG implements Resource {

    @Override
    public void getResource() throws IOException {
        try (InputStream is = Musc.class.getClassLoader().getResourceAsStream(Path.of("assets", "musc", "icon.png").toString())) {
            if (is != null) Files.copy(is, Path.of(ROOT.toString(), "pack.png"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
