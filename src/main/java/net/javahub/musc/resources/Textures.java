package net.javahub.musc.resources;

import net.javahub.musc.Musc;
import net.javahub.musc.records.RecordBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static net.javahub.musc.Musc.RECORDS;

class Textures implements Resource {

    private BufferedImage getOverlay(RecordBuilder.Record record) {
        BufferedImage overlay = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        int[][] pixels = new int[][]{
                {6, 6}, {7, 6}, {8, 6},
                {5, 7}, {6, 7}, {8, 7}, {9, 7},
                {6, 8}, {7, 8}, {8, 8}
        };
        for (int[] pixel : pixels) {
            int color = 0xFF000000 + record.hashCode() / 0xff;
            overlay.setRGB(pixel[0], pixel[1], color);
        }
        return overlay;
    }

    private BufferedImage getDisc(Path path) throws IOException {
        try (InputStream is = Musc.class.getClassLoader().getResourceAsStream("assets/musc/textures/item/disc.png")) {
            if (is != null) Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return ImageIO.read(path.toFile());
    }

    @Override
    public void getResource() throws IOException {
        for (RecordBuilder.Record record: RECORDS) {
            Path path = Template.TEXTURES.resolve(Path.of(record.getItemName() + ".png"));
            BufferedImage overlay = getOverlay(record);
            BufferedImage disc = getDisc(path);
            BufferedImage combined = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

            Graphics g = combined.getGraphics();
            g.drawImage(disc, 0, 0, null);
            g.drawImage(overlay, 0, 0, null);

            ImageIO.write(combined, "PNG", path.toFile());
        }
    }
}
