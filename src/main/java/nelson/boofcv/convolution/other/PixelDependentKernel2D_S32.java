package nelson.boofcv.convolution.other;

import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayU8;

public abstract class PixelDependentKernel2D_S32 extends Kernel2D_S32 {

    protected final GrayU8 input;

    public PixelDependentKernel2D_S32(GrayU8 input, int width, int[] data) {
        super(width);
        this.data = new int[width * width];
        System.arraycopy(data, 0, this.data, 0, this.data.length);
        this.input = input;
    }

    public PixelDependentKernel2D_S32(GrayU8 input, int width) {
        super(width);
        this.data = new int[width * width];
        this.input = input;
    }

    public PixelDependentKernel2D_S32(GrayU8 input, int width, int offset) {
        super(width, offset);
        this.data = new int[width * width];
        this.input = input;
    }

    protected PixelDependentKernel2D_S32(GrayU8 input) {
        super();
        this.input = input;
    }

    abstract void compute(int x, int y);

}
