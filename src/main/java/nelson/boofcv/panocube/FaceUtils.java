package nelson.boofcv.panocube;

import com.google.common.collect.Range;
import georegression.struct.point.Point3D_F64;

/**
 *
 */
public class FaceUtils {

    public static String getName(final Face face) {
        switch (face) {
            case Back:
                return "nz";
            case Left:
                return "nx";
            case Front:
                return "pz";
            case Right:
                return "px";
            case Top:
                return "py";
            case Bottom:
                return "ny";
        }
        throw new UnexpectedFaceException(face);
    }

    static Point3D_F64 getVector(final Face face, final int i, final int j, final double edge) {
        return getVectorInternal(override(face, j, edge), i, j, edge);
    }

    private static Point3D_F64 getVectorInternal(final Face face, final int i, final int j, final double edge) {
        double a = 2.0 * (double) i / edge;
        double b = 2.0 * (double) j / edge;
        switch (face) {
            case Back:
                return new Point3D_F64(-1.0, 1.0 - a, 3.0 - b);
            case Left:
                return new Point3D_F64(a - 3.0, -1.0, 3.0 - b);
            case Front:
                return new Point3D_F64(1.0, a - 5.0, 3.0 - b);
            case Right:
                return new Point3D_F64(7.0 - a, 1.0, 3.0 - b);
            case Top:
                return new Point3D_F64(b - 1.0, a - 5.0, 1.0);
            case Bottom:
                return new Point3D_F64(5.0 - b, a - 5.0, -1.0);
        }
        throw new UnexpectedFaceException(face);
    }

    static Range<Integer> getRange(final Face face, final double edge) {
        if (Face.Front.equals(face)) {
            return Range.closed(0, (int) (edge*3));
        } else {
            return Range.closed((int)edge, (int) (edge*2));
        }
    }

    private static Face override(final Face face, final int j, final double edge) {
        if (j < edge) {
            return Face.Top;
        } else if (j >= 2 * edge) {
            return Face.Bottom;
        } else {
            return face;
        }
    }

}
