package nelson.boofcv.play;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import boofcv.alg.color.ColorHsv;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;

public class SplitChannels {

    private static ListDisplayPanel panel = new ListDisplayPanel();

    public static void main(String[] args) {
        BufferedImage buffered = UtilImageIO.loadImage("D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\marble.jpg");
        Planar<GrayU8> image = new Planar<GrayU8>(GrayU8.class, buffered.getWidth(), buffered.getHeight(), 3);
        ConvertBufferedImage.convertFromPlanar(buffered, image, true, GrayU8.class);
        //GrayU8 gray = ConvertBufferedImage.convertFrom(buffered, (GrayU8) null);


        panel.addImage(VisualizeImageData.standard(image.getBand(0), null), "R");
        panel.addImage(VisualizeImageData.standard(image.getBand(1), null), "G");
        panel.addImage(VisualizeImageData.standard(image.getBand(2), null), "B");

        ShowImages.showWindow(panel, "Channels", true);

        ColorSpace instance = ColorSpace.getInstance(ColorSpace.TYPE_CMYK);
        //instance.fromRGB(image.bands)
    }
}
