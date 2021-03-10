package nelson.boofcv.panocube.app;

import boofcv.io.image.UtilImageIO;
import nelson.boofcv.panocube.Converter;
import nelson.boofcv.panocube.ConverterFactory;
import nelson.boofcv.panocube.Face;
import nelson.boofcv.panocube.FaceUtils;

import java.awt.image.BufferedImage;
import java.util.Map;

public class App {

    public static final String INPUT = "D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\ar-test.jpg";
    public static final String OUTPUT = "D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\temp\\";

    public static void main(String[] args) {
        final BufferedImage image = UtilImageIO.loadImage(INPUT);
        final Converter<BufferedImage> converter = ConverterFactory.fromBufferedImage(image);
        final BufferedImage full = converter.full();
        UtilImageIO.saveImage(full, OUTPUT + "full.png");
        final Map<Face, BufferedImage> faces = converter.faces();
        for (final Face f : faces.keySet()) {
            final String name = String.format(OUTPUT + "/%s.png", FaceUtils.getName(f));
            final BufferedImage face = faces.get(f);
            UtilImageIO.saveImage(face, name);
        }

    }

}
