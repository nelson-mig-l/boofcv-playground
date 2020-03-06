package nelson.boofcv.panocube;

import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;

import java.util.Map;

/**
 *
 */
public class BasicConverter implements Converter<Planar<GrayU8>> {

    private final Planar<GrayU8> cube;

    public BasicConverter(final Planar<GrayU8> input) {
        Planar<GrayU8> panorama;
        if (AspectRatio.hasCorrectAspectRation(input)) {
            panorama = input;
        } else {
            panorama = AspectRatio.correctAspectRatio(input);
        }
        cube = panorama.createNew(panorama.width, panorama.height * 3 / 2);
        ConvertUtils.convert(panorama, cube);
    }

    public Planar<GrayU8> full() {
        return cube;
    }

    public Map<Face, Planar<GrayU8>> faces() {
        return new ExplodeCube().explode(cube);
    }

}
