package nelson.boofcv.panocube;


import boofcv.abst.distort.FDistort;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;
import com.google.common.base.Optional;

/**
 * Created by Nelson on 03-Oct-18.
 */
public class Resizer {

    private final Optional<Integer> size;

    public Resizer() {
        size = Optional.absent();
    }
    public Resizer(final int resizeTo) {
        size = Optional.of(resizeTo);
    }

    public Planar<GrayU8> resize(final Planar<GrayU8> input) {
        if (size.isPresent()) {
            final Planar<GrayU8> output = input.createNew(size.get(), size.get());
            new FDistort(input, output).scaleExt().apply();
            return output;
        } else {
            return input;
        }
    }
}
