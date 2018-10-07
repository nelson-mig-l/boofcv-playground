package nelson.boofcv.panocube;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * https://visibleearth.nasa.gov/view_cat.php?categoryID=1484&p=2
 */
public class RunAll {

    private static final String DIR_INPUT = "./src/main/resources/input/";
    private static final String DIR_OUTPUT = "./src/main/resources/output/";
    private static final int DEFAULT_SIZE = 128;

    private static final List<Params> ALL = new ArrayList<Params>();
    static {
        ALL.add(paramsFor("milky", "/milky_way_hd.jpg", 1024));
        ALL.add(paramsFor("sun", "/sun.jpg"));
        ALL.add(paramsFor("mercury", "/mercury.jpg"));
        ALL.add(paramsFor("venus", "/venus.jpg"));
        ALL.add(paramsFor("earth", "/earth.jpg"));
        ALL.add(paramsFor("moon", "/moon.png", 64));
        ALL.add(paramsFor("mars", "/mars.jpg"));
        ALL.add(paramsFor("phobos", "/phobos.png", 64));
        ALL.add(paramsFor("deimos", "/deimos.png", 64));
        ALL.add(paramsFor("jupiter", "/jupiter.jpg"));
        ALL.add(paramsFor("saturn", "/saturn.jpg"));
        ALL.add(paramsFor("uranus", "/uranus.jpg"));
        ALL.add(paramsFor("neptune", "/neptune.jpg"));
        ALL.add(paramsFor("pluto", "/pluto.jpg"));
    }

    public static void main(final String[] args) {
        for (final Params params : ALL) {
            new Main(params).run();
        }
    }

    private static Params paramsFor(final String name, final String file) {
        return paramsFor(name, file, DEFAULT_SIZE);
    }

    private static Params paramsFor(final String name, final String file, final int size) {
        final Params params = new Params();
        params.input = new File(DIR_INPUT + file);
        params.output = new File(DIR_OUTPUT);
        params.size = size;
        params.name = name;
        return params;
    }

}
