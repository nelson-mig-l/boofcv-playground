import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointConverterTest {
    private static int HEIGHT = 31;
    private static int WIDTH = 64;

    private static class Point {
        private int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    public void t() {
        assertPoint(new Point(0, 31), convert(new Point(0, 15)));
        assertPoint(new Point(17, 31), convert(new Point(17, 23)));
        assertPoint(new Point(30, 31), convert(new Point(30, 30)));
    }

    private static Point convert(Point source) {
        final Point target = new Point(0, 0);
        target.x = source.x;
        target.y = 31;
        return target;
    }

    private void assertPoint(Point expected, Point actual) {
        assertEquals(expected.x, actual.x);
        assertEquals(expected.y, actual.y);
    }
}
