package dithering;

import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;

public class StuckiDithering extends TableDithering {

    /**
     *              X   8   4
     *      2   4   8   4   2
     *      1   2   4   2   1
     */
    public StuckiDithering() {
        super(new DitheringTable(5, 3));
        table.setValue(1, 0, 8);
        table.setValue(2, 0, 4);

        table.setValue(-2, 1, 2);
        table.setValue(-1, 1, 4);
        table.setValue(0, 1, 8);
        table.setValue(1, 1, 4);
        table.setValue(2, 1, 2);

        table.setValue(-2, 2, 1);
        table.setValue(-1, 2, 2);
        table.setValue(0, 2, 4);
        table.setValue(1, 2, 2);
        table.setValue(2, 2, 1);

        table.normalize();
    }

    @Override
    public GrayU8 apply(GrayU8 input) {
        GrayS32 target = input.createSameShape(GrayS32.class);
        for (int y = 0; y < input.height; y++) {
            int error;
            for (int x = 0; x < input.width; x++) {
                int source = input.get(x, y) + target.get(x, y);

                if (source >= 127) {
                    error = source - 255;
                    target.set(x, y, 255);
                } else {
                    error = source;
                    target.set(x, y, 0);
                }

                if (error != 0) {
                    for (int i = -2; i <= 2; i++) {
                        for (int j = 0; j <= 2; j++) {
                            if ((j == 0) && (i <= 0)) continue;
                            double tableValue = table.getValue(i, j);
                            if (tableValue == 0) continue;

                            int xStride = x + i;
                            int quickY = y + j;

                            if (xStride < 0) continue;
                            if (xStride >= target.width) continue;
                            if (quickY >= target.height) continue;

                            double e = target.get(xStride, quickY) + (error * table.getValue(i, j));
                            target.set(xStride, quickY, (int)e);
                        }
                    }
                }
            }
        }
        return createOutput(target);
    }
}
