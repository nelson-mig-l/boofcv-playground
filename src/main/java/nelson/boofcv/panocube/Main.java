package nelson.boofcv.panocube;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import com.google.common.base.Preconditions;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger("Main");

    public static void main(final String[] args) {
        final Params parameters = new Params();
        final CmdLineParser parser = new CmdLineParser(parameters);
        try {
            parser.parseArgument(args);
            new Main(parameters).run();
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

    private final Params params;

    public Main(final Params params) {
        this.params = params;
    }

    public void run() {
        final File output = createOutputDirectory();
        LOG.info("Creating cube...");
        final Planar<GrayU8> cube = convertPanoramaToCube();
        save(cube, output + "/cube.png");

        final Resizer r = params.size > 0 ? new Resizer(params.size) : new Resizer();
        LOG.info("Processing faces...");
        final Map<Face, Planar<GrayU8>> exploded = new ExplodeCube().explode(cube);
        for (final Face face : exploded.keySet()) {
            final Planar<GrayU8> resized = r.resize(exploded.get(face));
            final String name = String.format(output + "/%s.png", FaceUtils.getName(face));
            save(resized, name);
        }

        LOG.info("Done.");
    }

    private File createOutputDirectory() {
        final File output = new File(params.output.getAbsolutePath() + "/" + params.name);
        output.mkdir();
        return output;
    }

    private Planar<GrayU8> convertPanoramaToCube() {
        final Planar<GrayU8> panorama = load(params.input.getAbsolutePath());
        final Planar<GrayU8> cube = panorama.createNew(panorama.width, panorama.height * 3 / 2);
        new PanoramaToCube().converter(panorama, cube);
        return cube;
    }

    private Planar<GrayU8> load(final String name) {
        final BufferedImage image = UtilImageIO.loadImage(name);
        Preconditions.checkNotNull(image, name + " didn't load properly");
        return ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayU8.class));
    }

    private void save(final Planar<GrayU8> planar, final String name) {
        try {
            UtilImageIO.saveImage(planar, name);
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(name, e);
        }
    }

}
