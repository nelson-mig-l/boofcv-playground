package nelson.boofcv.console;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class ConsoleFont {

    private final Rectangle2D bounds;

    public ConsoleFont(final String fontName, final int fontSize) {
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        FontRenderContext frc = new FontRenderContext(null,
                RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
                RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);

        //Rectangle2D iBounds = font.getStringBounds("i", frc);
        //Rectangle2D mBounds = font.getStringBounds("m", frc);
        // compare i vs m to check monospaced

        bounds = font.getStringBounds("x", frc);
    }

    public double getHeight() {
        return bounds.getHeight();
    }

    public double getWidth() {
        return bounds.getWidth();
    }
}
