package nelson.boofcv.distort;

import boofcv.abst.distort.ConfigDeformPointMLS;
import boofcv.abst.distort.FDistort;
import boofcv.abst.distort.PointDeformKeyPoints;
import boofcv.alg.distort.PointToPixelTransform_F32;
import boofcv.alg.interpolate.InterpolationType;
import boofcv.factory.distort.FactoryDistort;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import georegression.struct.point.Point2D_F32;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/lessthanoptimal/BoofCV/blob/3898140b1ab435f766a176688aadb226e275a9e0/main/boofcv-geo/src/main/java/boofcv/alg/distort/RemovePerspectiveDistortion.java
 * https://boofcv.org/index.php?title=Example_Remove_Perspective_Distortion
 * http://boofcv.org/javadoc/index.html?boofcv/alg/distort/RemovePerspectiveDistortion.html
 */
public class Distort {

    public static void main(final String[] args) {
        final Distort instance = new Distort();
        final Planar<GrayU8> input = instance.load("d:/repository/java/panocube/src/main/resources/grid.jpg");
        final Planar<GrayU8> output = input.createSameShape();


        List<Point2D_F32> src = new ArrayList<Point2D_F32>();
        List<Point2D_F32> dst = new ArrayList<Point2D_F32>();

        src.add(new Point2D_F32(100, 100));
        src.add(new Point2D_F32(100, 150)); //
        src.add(new Point2D_F32(100, 200));
        src.add(new Point2D_F32(150, 200)); //
        src.add(new Point2D_F32(200, 200));
        src.add(new Point2D_F32(200, 150)); //
        src.add(new Point2D_F32(200, 100));
        src.add(new Point2D_F32(150, 100)); //

        dst.add(new Point2D_F32(50, 50));
        dst.add(new Point2D_F32(50, 150)); //
        dst.add(new Point2D_F32(50, 250));
        dst.add(new Point2D_F32(150, 250)); //
        dst.add(new Point2D_F32(250, 250));
        dst.add(new Point2D_F32(250, 150)); //
        dst.add(new Point2D_F32(250, 50));
        dst.add(new Point2D_F32(150, 50)); //

        ConfigDeformPointMLS config = new ConfigDeformPointMLS();
        PointDeformKeyPoints deform = FactoryDistort.deformMls(config);
        deform.setImageShape(input.width,input.height);
        deform.setSource(src);
        deform.setDestination(dst);

        final FDistort distorter = new FDistort();
        distorter.input(input);
        distorter.interp(InterpolationType.BILINEAR);
        distorter.output(output);
        distorter.transform(new PointToPixelTransform_F32(deform));
        distorter.apply();

        instance.save(output, "d:/repository/java/panocube/src/main/resources/demo_test.jpg");
    }

    private Planar<GrayU8> load(final String name) {
        final BufferedImage image = UtilImageIO.loadImage(name);
        return ConvertBufferedImage.convertFrom(image, true, ImageType.pl(3, GrayU8.class));
    }

    private void save(final Planar<GrayU8> planar, final String name) {
        UtilImageIO.saveImage(planar, name);
    }

}
