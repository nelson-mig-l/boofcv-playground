package nelson.boofcv.panocube;

/**
 * Created by Nelson on 12-Aug-18.
 */
class UnexpectedFaceException extends RuntimeException {

    private static final String MESSAGE = "Unexpected face value (%s)";

    UnexpectedFaceException(final Face face) {
        super(String.format(MESSAGE, face));
    }
}
