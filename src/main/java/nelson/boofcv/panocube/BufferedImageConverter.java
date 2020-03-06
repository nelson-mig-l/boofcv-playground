package nelson.boofcv.panocube;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class BufferedImageConverter implements Converter<BufferedImage> {

    private final int imageType;
    private final BasicConverter basic;

    BufferedImageConverter(final BufferedImage panorama) {
        final Planar<GrayU8> planar = ConvertBufferedImage.convertFrom(panorama, true, ImageType.pl(3, GrayU8.class));
        imageType = panorama.getType();
        basic = new BasicConverter(planar);
    }

    public BufferedImage full() {
        final Planar<GrayU8> full = basic.full();
        final BufferedImage output = new BufferedImage(full.width, full.height, imageType);
        return ConvertBufferedImage.convertTo(full, output, true);
    }

    public Map<Face, BufferedImage> faces() {
        final Map<Face, Planar<GrayU8>> faces = basic.faces();
        final HashMap<Face, BufferedImage> result = new HashMap<Face, BufferedImage>(faces.size());
        for (final Face f : faces.keySet()) {
            final Planar<GrayU8> face = faces.get(f);
            final BufferedImage output = new BufferedImage(face.width, face.height, imageType);
            ConvertBufferedImage.convertTo(face, output, true);
            result.put(f, output);
        }
        return result;
    }
}
