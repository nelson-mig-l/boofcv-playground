package nelson.boofcv.panocube;

import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class ExplodeCube {

    Map<Face, Planar<GrayU8>> explode(final Planar<GrayU8> input) {
        final Map<Face, Planar<GrayU8>> result = new HashMap<Face, Planar<GrayU8>>();
        final int faceWidth = input.width / 4;
        final int faceHeight = input.height / 3;
        result.put(Face.Back, copy(input, faceWidth, faceHeight, 0, 1, 1, 2));
        result.put(Face.Left, copy(input, faceWidth, faceHeight, 1, 1, 2, 2));
        result.put(Face.Front, copy(input, faceWidth, faceHeight, 2, 1, 3, 2));
        result.put(Face.Right, copy(input, faceWidth, faceHeight, 3, 1, 4, 2));
        result.put(Face.Top, copy(input, faceWidth, faceHeight, 2, 0, 3, 1));
        result.put(Face.Bottom, copy(input, faceWidth, faceHeight, 2, 2, 3, 3));
        return result;
    }

    private Planar<GrayU8> copy(final Planar<GrayU8> input, int width, int height, int mx0, int my0, int mx1, int my1) {
        return input.subimage(width * mx0, height * my0, width * mx1, height * my1);
    }

}
