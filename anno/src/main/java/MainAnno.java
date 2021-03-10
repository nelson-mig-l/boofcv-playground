import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;

import java.awt.image.BufferedImage;

public class MainAnno {

    public static final String EXAMPLE = "D:\\repository\\java\\panocube\\boofcv-playground\\anno\\src\\main\\resources\\example.bmp";
    public static final String OUTPUT = "D:\\repository\\java\\panocube\\boofcv-playground\\anno\\src\\main\\resources\\output.bmp";

    public static void main(String[] args) {
        final BufferedImage image = UtilImageIO.loadImage(EXAMPLE);
        final Planar<GrayU8> rgb = ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayU8.class));
        //final Planar<GrayU8> output = rgb.createNew(rgb.width / 2, rgb.height + 1);
        final Planar<GrayU8> output = rgb.createNew(rgb.width, rgb.height + 1);


        for (int x = 0; x < rgb.width; x++) {
            for (int y = 0; y < rgb.height; y++) {
                final int[] target = targetPos(x, y, output.height);
                final int i = target[0];
                final int j = target[1];
                System.out.println(x + "," + y + " -> " + i + "," + j);
                output.set24u8(i, j, rgb.get24u8(x, y));
            }
        }

        UtilImageIO.saveImage(output, OUTPUT);
    }

    private static int[] targetPos(int x, int y, int len) {
        int[] pt = new int[2];
        if (x > len) {
            pt[0] = x;
            pt[1] = y;
        } else {
            pt[0] = x;
            pt[1] = y;
        }

        int total = x + y;
        if (total == 61 || total == 62) {
            pt[0] = x;
            pt[1] = len-1;
        } else {
            pt[0]=0; pt[1]=0;
        }
        return pt;
    }

}
