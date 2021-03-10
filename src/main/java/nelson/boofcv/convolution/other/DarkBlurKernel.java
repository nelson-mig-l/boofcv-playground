package nelson.boofcv.convolution.other;

import boofcv.struct.image.GrayU8;

public class DarkBlurKernel extends PixelDependentKernel2D_S32 {

    public DarkBlurKernel(GrayU8 input) {
        super(input, 3);
        normal();
    }

    @Override
    void compute(int x, int y) {
        if (input.get(x, y) < 127) {
            blur();
        } else {
            normal();
        }
    }

    private void blur() {
        this.offset = 1;
        this.set(0, 0, 1);
        this.set(0, 1, 1);
        this.set(0, 2, 1);
        this.set(1, 0, 1);
        this.set(1, 1, 1);
        this.set(1, 2, 1);
        this.set(2, 0, 1);
        this.set(2, 1, 1);
        this.set(2, 2, 1);
    }

    private void normal() {
        this.offset = 1;
        this.set(0, 0, 0);
        this.set(0, 1, 0);
        this.set(0, 2, 0);
        this.set(1, 0, 0);
        this.set(1, 1, 1);
        this.set(1, 2, 0);
        this.set(2, 0, 0);
        this.set(2, 1, 0);
        this.set(2, 2, 0);
    }
}
