package nelson.boofcv.console;

import java.util.function.Function;

public abstract class ConsoleMapper implements Function<Integer, Character> {

    public static ConsoleMapper create(final String key) {
        return new ConsoleMapper() {
            @Override
            public Character apply(Integer index) {
                return key.charAt(index);
            }
        };
    }

}
