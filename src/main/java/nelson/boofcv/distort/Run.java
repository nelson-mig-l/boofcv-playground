package nelson.boofcv.distort;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import com.google.common.base.Preconditions;

import java.awt.image.BufferedImage;

/**
 * https://stackoverflow.com/questions/29678510/convert-21-equirectangular-panorama-to-cube-map/29681646#
 */
public class Run {


    public static final String INPUT = "d:/repository/java/panocube/src/main/resources/grid.jpg";
    public static final String OUTPUT = "d:/repository/java/panocube/src/main/resources/test.jpg";


    public static void main(final String[] args) {
        final Planar<GrayU8> in = load(INPUT);
        final double height = (double) (in.getHeight()) * 3.0 / 2.0;
        final Planar<GrayU8> out = in.createNew(in.getWidth(), (int) height);
        new CubeConverter().convert(in, out);
        save(out, OUTPUT);
    }

    private static Planar<GrayU8> load(final String name) {
        final BufferedImage image = UtilImageIO.loadImage(name);
        Preconditions.checkNotNull(image, name + " didn't load properly");
        return ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayU8.class));
    }

    private static void save(final Planar<GrayU8> planar, final String name) {
        try {
            UtilImageIO.saveImage(planar, name);
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(name, e);
        }
    }
}
