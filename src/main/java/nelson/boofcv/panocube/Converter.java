package nelson.boofcv.panocube;

import java.util.Map;

/**
 *
 * @param <T>
 */
public interface Converter<T> {

    T full();

    Map<Face, T> faces();
}
