package nelson.boofcv.panocube;

import java.awt.image.BufferedImage;

/**
 *
 */
public class ConverterFactory {

    public static Converter<BufferedImage> fromBufferedImage(final BufferedImage image) {
        return new BufferedImageConverter(image);
    }
}
