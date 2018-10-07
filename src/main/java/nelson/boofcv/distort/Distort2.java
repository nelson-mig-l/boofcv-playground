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
import georegression.struct.point.Point2D_I32;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.floor;

/**
 * https://github.com/lessthanoptimal/BoofCV/blob/3898140b1ab435f766a176688aadb226e275a9e0/main/boofcv-geo/src/main/java/boofcv/alg/distort/RemovePerspectiveDistortion.java
 * https://boofcv.org/index.php?title=Example_Remove_Perspective_Distortion
 * http://boofcv.org/javadoc/index.html?boofcv/alg/distort/RemovePerspectiveDistortion.html
 */
public class Distort2 {

    public static void main(final String[] args) {
        final Distort2 instance = new Distort2();
        final Planar<GrayU8> input = instance.load("d:/repository/java/panocube/src/main/resources/grid.jpg");
        final Planar<GrayU8> output = input.createSameShape();


        List<Point2D_F32> src = new ArrayList<Point2D_F32>();
        List<Point2D_F32> dst = new ArrayList<Point2D_F32>();

        final Projection proj = new Projection();
        final CubeConverter conv = new CubeConverter();

        final int width = input.getWidth();
        final int height = input.getHeight();
        final int edge = width / 4;
        System.out.println("start");

        for (int x0 = 0; x0 < width; x0+=100) {
            for (int y0 = 0; y0 < height; y0+=100) {
                src.add(new Point2D_F32(x0, y0));
                final double phi = x0 * 2.0 * PI / width;
                final double theta = y0 * PI / height;
                final Projection.Result res = proj.project(theta, phi);
                final Point2D_I32 ptI = conv.toImage(res, edge);
                final Point2D_F32 ptF = new Point2D_F32(ptI.x, ptI.y);
                dst.add(ptF);
            }
        }
        System.out.println("points ready");
        ConfigDeformPointMLS config = new ConfigDeformPointMLS();
        PointDeformKeyPoints deform = FactoryDistort.deformMls(config);
        deform.setImageShape(input.width,input.height);
        deform.setSource(src);
        deform.setDestination(dst);
        System.out.println("will distort");
        final FDistort distorter = new FDistort();
        distorter.input(input);
        distorter.interp(InterpolationType.BILINEAR);
        distorter.output(output);
        distorter.transform(new PointToPixelTransform_F32(deform));
        distorter.apply();
        System.out.println("saving");
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
