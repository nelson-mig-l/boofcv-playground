package nelson.boofcv.distort;

import static java.lang.Math.*;

public class Projection {

    public enum Type {
        TOP, BOTTOM, LEFT, FRONT, RIGHT, BACK;
    }

    public static class Result {
        public final Type t;
        public final double x, y, z;

        Result(final Type t, final double x, final double y, final double z) {
            this.t = t;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public Result project(final double theta, final double phi) {
        if (theta < 0.615) {
            return top(theta, phi);
        } else if (theta > 2.527) {
            return bottom(theta, phi);
        } else if ((phi <= PI / 4) || (phi > 7 * PI / 4)) {
            return left(theta, phi);
        } else if ((phi > PI / 4) && (phi <= 3 * PI / 4)) {
            return front(theta, phi);
        } else if ((phi > 3 * PI / 4) && (phi <= 5 * PI / 4)) {
            return right(theta, phi);
        } else if ((phi > 5 * PI / 4) && (phi <= 7 * PI / 4)) {
            return back(theta, phi);
        }
        throw new RuntimeException();
    }

    private Result top(final double theta, final double phi) {
        final double x = tan(theta) * cos(phi);
        final double y = tan(theta) * sin(phi);
        final double z = 1.0;
        return new Result(Type.TOP, x, y, z);
    }

    private Result bottom(final double theta, final double phi) {
        final double x = -tan(theta) * cos(phi);
        final double y = -tan(theta) * sin(phi);
        final double z = -1.0;
        return new Result(Type.BOTTOM, x, y, z);
    }

    private Result left(final double theta, final double phi) {
        final double x = 1.0;
        final double y = tan(phi);
        final double z = cot(theta) / cos(phi);
        if (z < -1) {
            return bottom(theta, phi);
        }
        if (z > 1) {
            return top(theta, phi);
        }
        return new Result(Type.LEFT, x, y, z);
    }

    private Result front(final double theta, final double phi) {
        final double x = tan(phi - PI / 2);
        final double y = 1.0;
        final double z = cot(theta) / cos(phi - PI / 2);
        if (z < -1) {
            return bottom(theta, phi);
        }
        if (z > 1) {
            return top(theta, phi);
        }
        return new Result(Type.FRONT, x, y, z);
    }

    private Result right(final double theta, final double phi) {
        final double x = -1;
        final double y = tan(phi);
        final double z = -cot(theta) / cos(phi);
        if (z < -1) {
            return bottom(theta, phi);
        }
        if (z > 1) {
            return top(theta, phi);
        }
        return new Result(Type.RIGHT, x, -y, z);
    }

    private Result back(final double theta, final double phi) {
        final double x = tan(phi - 3.0 * PI / 2);
        final double y = -1;
        final double z = cot(theta) / cos(phi - 3.0 * PI / 2);
        if (z < -1) {
            return bottom(theta, phi);
        }
        if (z > 1) {
            return top(theta, phi);
        }
        return new Result(Type.BACK, -x, y, z);
    }


    private static double cot(final double v) {
        return 1 / tan(v);
    }


}
