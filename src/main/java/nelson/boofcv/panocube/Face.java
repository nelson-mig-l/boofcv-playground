package nelson.boofcv.panocube;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum Face {
    Back, Left, Front, Right, Top, Bottom;

    private static final Map<Integer, Face> lookup = new HashMap<Integer, Face>(6);
    static {
        lookup.put(0, Back);
        lookup.put(1, Left);
        lookup.put(2, Front);
        lookup.put(3, Right);
        lookup.put(4, Top);
        lookup.put(5, Bottom);
    }

    static Face fromIndex(final int index) {
        return lookup.get(index);
    }
}
