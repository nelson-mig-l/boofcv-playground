package nelson.boofcv.convolution;

import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.convolve.Kernel2D_F32;
import boofcv.struct.convolve.Kernel2D_F32;

import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static final File INPUT =
            new File("D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\input\\earth.jpg");

    public static final File OUTPUT =
            new File("D:\\repository\\java\\panocube\\boofcv-playground\\src\\main\\resources\\out.jpg");

    public static void main(String[] args) throws IOException {
        BufferedImage buffered = ImageIO.read(INPUT);
        Planar<GrayU8> input = ConvertBufferedImage.convertFrom(buffered, true, ImageType.pl(3, GrayU8.class));
        Planar<GrayS32> output = new Planar<GrayS32>(GrayS32.class, input.getWidth(), input.getHeight(), 3);
        //final Planar<GrayU8> output = input.createNew(input.getWidth(), input.getHeight());
        //final Kernel2D_F32 kernel = normalizeKernel(createKernel());
        final Kernel2D_S32 kernel = createKernel();

        GConvolveImageOps.convolve(kernel, input, output);

        final Planar<GrayU8> normalized = normalize(output, kernel.computeSum());

        UtilImageIO.saveImage(normalized, OUTPUT.getAbsolutePath());
    }

    private static Kernel2D_S32 createKernel() {
        // By default 2D kernels will be centered around width/2
        Kernel2D_S32 kernel = new Kernel2D_S32(3);
        kernel.set(0, 0, -1);
        kernel.set(2, 0, +1);
        kernel.set(0, 1, -2);
        kernel.set(2, 1, +2);
        kernel.set(0, 2, -1);
        kernel.set(2, 2, +1);
        return kernel;
    }

    private static Kernel2D_S32 createKernel2() {
        // By default 2D kernels will be centered around width/2
        Kernel2D_S32 kernel = new Kernel2D_S32(3);
        kernel.set(1, 2, 7);
        kernel.set(2, 0, 3);
        kernel.set(2, 1, 5);
        kernel.set(2, 2, 1);
        return kernel;
    }

    private static Kernel2D_F32 normalizeKernel(Kernel2D_F32 kernel) {
        final Kernel2D_F32 normalized = new Kernel2D_F32(kernel.getWidth());
        final double sum = kernel.computeSum();
        for (int y = 0; y < kernel.width; y++) {
            for (int x =0; x<kernel.width; x++) {
                final double n = kernel.get(x, y) / sum;
                normalized.set(x, y, (float) n);
            }
        }
        return normalized;
    }

    private static Planar<GrayU8> normalize(final Planar<GrayS32> input, final int sum) {
        final Planar<GrayU8> output = new Planar<GrayU8>(GrayU8.class, input.getWidth(), input.getHeight(), 3);
        int indexOutput = 0;
        for (int y = 0; y < input.height; y++) {
            int indexInput = input.startIndex + y * input.stride;
            for (int x = 0; x < input.width; x++, indexOutput++, indexInput++) {
                for (int b = 0; b < input.getNumBands(); b++) {
                    final double raw = input.getBand(b).data[indexInput];
                    final double scaled = raw / sum;
                    output.getBand(b).data[indexOutput] = (byte) clamp(scaled, 0, 255);
                }
            }
        }
        return output;
    }

    private static int clamp(final double val, final int min, final int max) {
        int v = (int) val;
        return Math.max(min, Math.min(max, v));
    }


}
