package nelson.boofcv.console;

import boofcv.alg.color.ColorHsv;
import boofcv.alg.misc.GPixelMath;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MainColor {

    public static final String PATH = "D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\";
    public static final String FILE = "marble.jpg";
    //public static final String FILE = "github.png";

    public static void main(String[] args) {

        final ConsoleFont font = new ConsoleFont("Consolas", 12);
        final int h = (int) font.getHeight();
        final int w = (int) font.getWidth();

        BufferedImage image = UtilImageIO.loadImage(PATH + FILE);
        final Planar<GrayU8> input = ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayU8.class));
        //GrayU8 gray = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);

        System.out.print(ConsoleColors.CYAN);
        System.out.print("Ready");
        System.out.println(ConsoleColors.RESET);
        String key = " ░▒▓█";
        final ConsoleMapper mapper = ConsoleMapper.create(key);
        double scale = (key.length()-1.0) / 256.0;

        final int lines = input.height / h;
        final int columns = input.width / w;
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                final Planar<GrayU8> subimage = input.subimage(
                        column * w,
                        line * h,
                        (column + 1) * w,
                        (line + 1) * h);
                //System.out.println("scale is "+ scale);
                //GPixelMath.multiply(subimage, scale, subimage);

                final int r = subimage.getBand(0).get(0, 0);
                final int g = subimage.getBand(1).get(0, 0);
                final int b = subimage.getBand(2).get(0, 0);
                double[] hsv = new double[3];
                ColorHsv.rgbToHsv(r, g, b, hsv);
                final int pixel = (int) (hsv[2]*scale);
                setColor(r, g, b);
                System.out.print(mapper.apply(pixel)); // get first pixel - TODO: average
                System.out.print(ConsoleColors.RESET);
            }
            System.out.println();
        }
    }

    private static void setColor(int r, int g, int b) {
        int[] rgb = {r, g, b};
        int max = Arrays.stream(rgb).max().getAsInt();
// find the index of that value
        int index = Arrays.asList(r,g,b).indexOf(max);
        switch (index) {
            case 0:
                System.out.print(ConsoleColors.RED_BACKGROUND);
                break;
            case 1:
                System.out.print(ConsoleColors.GREEN_BACKGROUND);
                break;
            case 2:
                System.out.print(ConsoleColors.BLUE_BACKGROUND);
                break;
            default:
                System.out.print(ConsoleColors.BLACK_BACKGROUND);
        }
    }
}
