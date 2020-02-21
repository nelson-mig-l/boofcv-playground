package nelson.boofcv.play;

import java.awt.image.BufferedImage;

import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.border.BorderType;
import boofcv.struct.border.ImageBorder;
import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;

public class Convolution {

    private static ListDisplayPanel panel = new ListDisplayPanel();

    public static void main(String[] args) {
        BufferedImage buffered = UtilImageIO.loadImage("C:\\repo\\tomtom\\tools\\azure-blob-demo\\src\\main\\resources\\signs\\79px-Znak_F-8.svg.png");
        //ConvertBufferedImage.
        GrayU8 gray = ConvertBufferedImage.convertFrom(buffered, (GrayU8) null);
        convolve2D(gray);
        ShowImages.showWindow(panel, "Convolution Examples", true);
    }

    private static void convolve2D(GrayU8 gray) {
        // By default 2D kernels will be centered around width/2
        Kernel2D_S32 kernel = new Kernel2D_S32(3);
//        kernel.set(1, 0, 2);
//        kernel.set(2, 1, 2);
//        kernel.set(0, 1, -2);
//        kernel.set(1, 2, -2);
		kernel.set(1, 2, 2);
		kernel.set(0, 2, 1);
		kernel.set(1, 2, 1);

        // Output needs to handle the increased domain after convolution.  Can't be 8bit
        GrayS16 output = new GrayS16(gray.width, gray.height);
        ImageBorder<GrayU8> border = FactoryImageBorder.wrap(BorderType.EXTENDED, gray);

        GConvolveImageOps.convolve(kernel, gray, output, border);
        panel.addImage(VisualizeImageData.standard(output, null), "2D Kernel");
    }

}
