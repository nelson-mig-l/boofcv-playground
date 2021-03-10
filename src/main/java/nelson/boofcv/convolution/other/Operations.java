package nelson.boofcv.convolution.other;

import boofcv.struct.image.GrayU8;
import nelson.boofcv.convolution.my.AbstractInputDependentKernel;

public class Operations {

    public static void convolve(PixelDependentKernel2D_S32 kernel, GrayU8 input, GrayU8 output) {
        int offset = kernel.getOffset();
        int width = input.getWidth();
        int height = input.getHeight();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int startX = x - offset;
                int endX = startX + kernel.getWidth();
                if (startX < 0) {
                    startX = 0;
                }

                if (endX > width) {
                    endX = width;
                }

                int startY = y - offset;
                int endY = startY + kernel.getWidth();
                if (startY < 0) {
                    startY = 0;
                }

                if (endY > height) {
                    endY = height;
                }

                int total = 0;
                int weight = 0;

                kernel.compute(x, y);

                for (int i = startY; i < endY; ++i) {
                    for (int j = startX; j < endX; ++j) {
                        int v = kernel.get(j - x + offset, i - y + offset);
                        total += input.get(j, i) * v;
                        weight += v;
                    }
                }
                //weight = weight == 0 ? 1 : weight;// custom code
                output.set(x, y, (total + weight / 2) / weight);
            }
        }

    }
}
