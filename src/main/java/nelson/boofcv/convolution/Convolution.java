package nelson.boofcv.convolution;

import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayU8;

public class Convolution {

    static class InputDependentKernel extends Kernel2D_S32 {

        GrayU8 input;

        InputDependentKernel(GrayU8 input) {
            super(3);
            this.input = input;


            this.offset = 1;
            this.set(0, 0, 1);
            this.set(0, 1, 1);
            this.set(0, 2, 1);
            this.set(1, 0, 1);
            this.set(1, 1, 6);
            this.set(1, 2, 1);
            this.set(2, 0, 1);
            this.set(2, 1, 1);
            this.set(2, 2, 1);
        }

        /**
         *
         * @param x input x
         * @param y input y
         * @param i kernel x
         * @param j kernel y
         * @return value at kernel x, kernel y
         */
        int output(int x, int y, int i, int j) {
            final int value = input.get(x, y);
            if (value < 64) {
                return (i == 1 && j == 1) ? 1 : 0;
            } else {
                return this.get(i, j);
            }
        }

    }

    public static void convolve(InputDependentKernel kernel, GrayU8 input, GrayU8 output) {
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

                for (int i = startY; i < endY; ++i) {
                    for (int j = startX; j < endX; ++j) {
                        int v = kernel.output(x, y, j - x + offset, i - y + offset);
                        total += input.get(j, i) * v;
                        weight += v;
                    }
                }

                output.set(x, y, (total + weight / 2) / weight);
            }
        }

    }

}
