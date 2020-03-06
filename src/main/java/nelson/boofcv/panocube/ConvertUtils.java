package nelson.boofcv.panocube;

import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;
import com.google.common.collect.Range;
import georegression.struct.point.Point3D_F64;

/**
 *
 */
class ConvertUtils {

    static void convert(final Planar<GrayU8> input, final Planar<GrayU8> output) {
        final double edge = input.width / 4;
        for (int i = 0; i < output.width; i++) {
            final Face face = Face.fromIndex((int) (i / edge));
            final Range<Integer> range = FaceUtils.getRange(face, edge);
            for (int j = range.lowerEndpoint(); j < range.upperEndpoint(); j++) {
                final Point3D_F64 vector = FaceUtils.getVector(face, i, j, edge);
                final double theta = Math.atan2(vector.y, vector.x);
                final double r = Math.hypot(vector.x, vector.y);
                final double phi = Math.atan2(vector.z, r);

                final double uf = 2.0 * edge * (theta + Math.PI) / Math.PI;
                final double vf = 2.0 * edge * (Math.PI / 2 - phi) / Math.PI;

                final int ui = (int) Math.floor(uf);
                final int vi = (int) Math.floor(vf);
                final int u2 = ui + 1;
                final int v2 = vi + 1;
                final double mu = uf - ui;
                final double nu = vf - vi;

                for (int bandIndex = 0; bandIndex < input.getNumBands(); bandIndex++) {
                    final int bandValue = interpolate(input.getBand(bandIndex), ui, u2, vi, v2, mu, nu);
                    output.getBand(bandIndex).set(i, j, bandValue);
                }
            }
        }
    }

    static private int interpolate(final GrayU8 input, final int ui, final int u2, final int vi, final int v2, final double mu, final double nu) {
        final int xi = ui % input.width;
        final int x2 = u2 % input.width;
        final int yi = clamp(vi, 0, input.height - 1);
        final int y2 = clamp(v2, 0, input.height - 1);

        final int a = input.get(xi, yi);
        final int b = input.get(x2, yi);
        final int c = input.get(xi, y2);
        final int d = input.get(x2, y2);

        final double value = a * (1 - mu) * (1 - nu) + b * (mu) * (1 - nu) + c * (1 - mu) * nu + d * mu * nu;
        return (int) value;
    }

    private static int clamp(final int val, final int min, final int max) {
        return Math.max(min, Math.min(max, val));
    }

}
