
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.*;

import java.awt.image.BufferedImage;

public class Halftone {

    private static final String FILE_ADDITION = "_halftoned";

    private final String path;

    public Halftone(final String path) {
        this.path = path;
    }

    public void make() {
        final String outfile = this.path.concat(FILE_ADDITION);

        final BufferedImage image = UtilImageIO.loadImage(path);
        final Planar<GrayF32> rgb = ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayF32.class));
        final Planar<GrayF32> cmyk = grc(rgb);

    }

    private Planar<GrayF32> grc(final Planar<GrayF32> rgb) {
        final Planar<GrayF32> cmyk = new Planar<GrayF32>(GrayF32.class, rgb.getWidth(), rgb.getHeight(), 4);
//        final int length = rgb.getBand(0).data.length;
//        for (int i = 0; i < length; i++) {
//            float[] out = new float[4];
//            ColorCmyk.rgbToCmyk(
//                    rgb.getBand(0).data[i],
//                    rgb.getBand(1).data[i],
//                    rgb.getBand(2).data[i],
//                    out
//            );
//            cmyk.getBand(0).data[i] = out[0];
//            cmyk.getBand(1).data[i] = out[1];
//            cmyk.getBand(2).data[i] = out[2];
//            cmyk.getBand(3).data[i] = out[3];
//        }
        return cmyk;
    }
}
