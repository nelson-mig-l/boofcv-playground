package nelson.boofcv.console;

import boofcv.alg.misc.GPixelMath;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

import java.awt.image.BufferedImage;

public class Main {

    public static final String PATH = "D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\";
    public static final String FILE = "marble.jpg";
    //public static final String FILE = "github.png";

    public static void main(String[] args) {
        final ConsoleFont font = new ConsoleFont("Consolas", 12);
        final int h = (int) font.getHeight();
        final int w = (int) font.getWidth();
        System.out.println(w + "x" + h);
        BufferedImage image = UtilImageIO.loadImage(PATH + FILE);
        GrayU8 gray = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);
        System.out.println(gray.width + "x" + gray.height);

        final int lines = gray.height / h;
        final int columns = gray.width / w;
        System.out.println(columns + "x" + lines);
        System.out.println(columns*w + "x" + lines*h);

        String key = " ░▒▓█";
        final ConsoleMapper mapper = ConsoleMapper.create(key);
        double scale = (key.length()-1.0) / 256.0;
        System.out.println("scale is "+ scale);
        GPixelMath.multiply(gray, scale, gray);

        new ConsolePrinter(gray, font, mapper).print();

    }

}
