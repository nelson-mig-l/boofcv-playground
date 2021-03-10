package nelson.boofcv.console;

import boofcv.struct.image.GrayU8;

public class ConsolePrinter {

    private final GrayU8 image;
    private final ConsoleFont font;
    private final int charHeight;
    private final int charWidth;
    private final ConsoleMapper mapper;

    public ConsolePrinter(final GrayU8 image, final ConsoleFont font, final ConsoleMapper mapper) {
        this.image = image;
        this.font = font;
        this.charHeight = (int) font.getHeight();
        this.charWidth = (int) font.getWidth();
        this.mapper = mapper;
    }

    public void print() {
        final int lines = image.height / charHeight;
        final int columns = image.width / charWidth;
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                final GrayU8 subimage = image.subimage(
                        column * charWidth,
                        line * charHeight,
                        (column + 1) * charWidth,
                        (line + 1) * charHeight);
                System.out.print(mapper.apply(subimage.get(0, 0))); // get first pixel - TODO: average
            }
            System.out.println();
        }
    }

}
