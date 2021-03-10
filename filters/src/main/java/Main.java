import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.*;

import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        final String input = "D:\\repository\\java\\panocube\\boofcv-playground\\filters\\src\\main\\resources\\test.png";
        final String output = "D:\\repository\\java\\panocube\\boofcv-playground\\filters\\src\\main\\resources\\out.png";
        final BufferedImage image = UtilImageIO.loadImage(input);
        final GrayF32 plane = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        final GrayF32 clone = plane.clone();

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;

        final KuwaharaFilterScalar filter = new KuwaharaFilterScalar(3, 1.2f);
        for (int x = 0; x < plane.width; x++) {
            for (int y = 0; y < plane.height; y++) {
                final float pixel = filter.doPixel(plane, x, y);
                if (pixel > max) max = pixel;
                if (pixel < min) min = pixel;
                clone.set(x, y, pixel/ 65406f * 255);
            }
        }

        System.out.println(max);
        System.out.println(min);

        UtilImageIO.saveImage(clone, output);
    }
}
