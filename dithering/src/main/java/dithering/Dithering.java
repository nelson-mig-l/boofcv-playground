package dithering;

import boofcv.struct.image.GrayU8;

public interface Dithering {

    GrayU8 apply(GrayU8 input);

}
