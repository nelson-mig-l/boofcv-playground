package nelson.boofcv.distort;

import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;
import georegression.struct.point.Point2D_I32;

import static java.lang.Math.PI;

/**
 * Created by Nelson on 06-Oct-18.
 */
public class CubeConverter {

    private static final Projection PROJECTION = new Projection();

    public void convert(final Planar<GrayU8> in, final Planar<GrayU8> out) {
        final int edge = in.getWidth() / 4;
        for (int i = 0; i < in.getWidth(); i++) {
            for (int j = 0; j < in.getHeight(); j++) {
                final int r = in.getBand(0).get(i, j);
                final int g = in.getBand(1).get(i, j);
                final int b = in.getBand(2).get(i, j);
                final double phi = i * 2.0 * PI / in.getWidth();
                final double theta = j * PI / in.getHeight();
                final Projection.Result result = PROJECTION.project(theta, phi);
                final Point2D_I32 xy = toImage(result, edge);

                final int x = clamp(xy.getX(), 0, out.getWidth() - 1);
                final int y = clamp(xy.getY(), 0, out.getHeight() - 1);

                out.getBand(0).set(x, y, r);
                out.getBand(1).set(x, y, g);
                out.getBand(2).set(x, y, b);
            }
        }
    }

    Point2D_I32 toImage(final Projection.Result c, final int edge) {
        final double x;
        final double y;
        switch (c.t) {
            case TOP:
                x = edge * (3 - c.x) / 2;
                y = edge * (1 + c.y) / 2;
                break;
            case BOTTOM:
                x = edge * (3 - c.x) / 2;
                y = edge * (5 - c.y) / 2;
                break;
            case LEFT:
                x = edge * (c.y + 1) / 2;
                y = edge * (3 - c.z) / 2;
                break;
            case FRONT:
                x = edge * (c.x + 3) / 2;
                y = edge * (3 - c.z) / 2;
                break;
            case RIGHT:
                x = edge * (5 - c.y) / 2;
                y = edge * (3 - c.z) / 2;
                break;
            case BACK:
                x = edge * (7 - c.x) / 2;
                y = edge * (3 - c.z) / 2;
                break;
            default:
                throw new RuntimeException();
        }
        return new Point2D_I32((int) x, (int) y);
    }

    private static int clamp(final int val, final int min, final int max) {
        return Math.max(min, Math.min(max, val));
    }


}
