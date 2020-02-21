package nelson.boofcv.panocube;

import boofcv.alg.misc.GImageMiscOps;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;

public class AspectRatio {

    public static boolean hasCorrectAspectRation(final Planar<GrayU8> input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        return width == height * 2;
    }

    public static Planar<GrayU8> correctAspectRatio(final Planar<GrayU8> input) {
        final Planar<GrayU8> output = createOutput(input);
        int xDelta = output.getWidth() - input.getWidth();
        int yDelta = output.getHeight() - input.getHeight();
        GImageMiscOps.copy(0, 0, xDelta / 2, yDelta / 2, input.getWidth(), input.getHeight(), input, output);
        return output;
    }

    private static Planar<GrayU8> createOutput(final Planar<GrayU8> input) {
        final int width = input.getWidth();
        final int height = input.getHeight();
        if (width > height * 2) {
            return input.createNew(width, width / 2);
        } else {
            return input.createNew(height * 2, height);
        }
    }


}
