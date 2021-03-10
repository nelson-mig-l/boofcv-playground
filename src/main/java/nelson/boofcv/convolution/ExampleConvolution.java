package nelson.boofcv.convolution;

import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.gui.ListDisplayPanel;
import boofcv.gui.image.ShowImages;
import boofcv.gui.image.VisualizeImageData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.border.BorderType;
import boofcv.struct.border.ImageBorder;
import boofcv.struct.convolve.Kernel1D_S32;
import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;

import nelson.boofcv.convolution.other.*;

import java.awt.image.BufferedImage;

/* Several examples demonstrating convolution.
 *
 * @author Peter Abeles
 */
public class ExampleConvolution {

    public static final String PATH = "D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\test.png";
    private static ListDisplayPanel panel = new ListDisplayPanel();

    public static void main(String[] args) {
        BufferedImage image = UtilImageIO.loadImage(PATH);

        GrayU8 gray = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);

        panel.addImage(VisualizeImageData.standard(gray, null), "input");
//        custom(gray);
//        hyper(gray);
       //my(gray);

        //kuwahara(gray);
        //other(gray);
        //another(gray);
        lightAndDark(gray);


//        convolve1D(gray);
//        convolve2D(gray);
//        normalize2D(gray);


        ShowImages.showWindow(panel, "Convolution Examples", true);
    }

    /**
     * Convolves a 1D kernel horizontally and vertically
     */
    private static void convolve1D(GrayU8 gray) {
        ImageBorder<GrayU8> border = FactoryImageBorder.wrap(BorderType.EXTENDED, gray);
        Kernel1D_S32 kernel = new Kernel1D_S32(2);
        kernel.offset = 1; // specify the kernel's origin
        kernel.data[0] = 1;
        kernel.data[1] = -1;

        GrayS16 output = new GrayS16(gray.width, gray.height);

        GConvolveImageOps.horizontal(kernel, gray, output, border);
        panel.addImage(VisualizeImageData.standard(output, null), "1D Horizontal");

        GConvolveImageOps.vertical(kernel, gray, output, border);
        panel.addImage(VisualizeImageData.standard(output, null), "1D Vertical");


        Kernel2D_S32 k = new Kernel2D_S32(3);
        k.set(1, 0, 1);
        k.set(2, 1, 1);
        k.set(0, 1, -1);
        k.set(1, 2, -1);
        GConvolveImageOps.convolve(k, gray, output, border);
        panel.addImage(VisualizeImageData.standard(output, null), "xxx");
    }

    /**
     * Convolves a 2D kernel
     */
    private static void convolve2D(GrayU8 gray) {
        // By default 2D kernels will be centered around width/2
        Kernel2D_S32 kernel = new Kernel2D_S32(3);
        kernel.set(1, 0, 2);
        kernel.set(2, 1, 2);
        kernel.set(0, 1, -2);
        kernel.set(1, 2, -2);

        // Output needs to handle the increased domain after convolution.  Can't be 8bit
        GrayS16 output = new GrayS16(gray.width, gray.height);
        ImageBorder<GrayU8> border = FactoryImageBorder.wrap(BorderType.EXTENDED, gray);

        GConvolveImageOps.convolve(kernel, gray, output, border);
        panel.addImage(VisualizeImageData.standard(output, null), "2D Kernel");
    }

    /**
     * Convolves a 2D normalized kernel.  This kernel is divided by its sum after computation.
     */
    private static void normalize2D(GrayU8 gray) {
        // Create a Gaussian kernel with radius of 3
        Kernel2D_S32 kernel = FactoryKernelGaussian.gaussian2D(GrayU8.class, -1, 3);
        // Note that there is a more efficient way to compute this convolution since it is a separable kernel
        // just use BlurImageOps instead.

        // Since it's normalized it can be saved inside an 8bit image
        GrayU8 output = new GrayU8(gray.width, gray.height);
        GConvolveImageOps.convolveNormalized(kernel, gray, output);
        panel.addImage(VisualizeImageData.standard(output, null), "2D Normalized Kernel");
    }

    private static void custom(GrayU8 gray) {
        Kernel2D_S32 kernel = new Kernel2D_S32(3);
        kernel.offset = 1;
        kernel.set(0, 0, 1);
        kernel.set(0, 1, 1);
        kernel.set(0, 2, 1);
        kernel.set(1, 0, 1);
        kernel.set(1, 1, 1);
        kernel.set(1, 2, 1);
        kernel.set(2, 0, 1);
        kernel.set(2, 1, 1);
        kernel.set(2, 2, 1);

        GrayU8 output = new GrayU8(gray.width, gray.height);
        GConvolveImageOps.convolveNormalized(kernel, gray, output);
        panel.addImage(VisualizeImageData.standard(output, null), "Custom");
    }

    private static void hyper(GrayU8 gray) {
        final Convolution.InputDependentKernel kernel = new Convolution.InputDependentKernel(gray);

        GrayU8 output = new GrayU8(gray.width, gray.height);
        Convolution.convolve(kernel, gray, output);
        //GConvolveImageOps.convolveNormalized(kernel, gray, output);
        panel.addImage(VisualizeImageData.standard(output, null), "Hyper");
    }

    private static void lightAndDark(GrayU8 gray) {
        final PixelDependentKernel2D_S32 light = new LightBlurKernel(gray);
        GrayU8 outputL = new GrayU8(gray.width, gray.height);
        Operations.convolve(light, gray, outputL);
        panel.addImage(VisualizeImageData.standard(outputL, null), "light");

        final PixelDependentKernel2D_S32 dark = new DarkBlurKernel(gray);
        GrayU8 outputD = new GrayU8(gray.width, gray.height);
        Operations.convolve(dark, gray, outputD);
        panel.addImage(VisualizeImageData.standard(outputD, null), "dark");
    }
}