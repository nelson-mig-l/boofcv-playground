package nelson.boofcv.distort;

import boofcv.abst.distort.ConfigDeformPointMLS;
import boofcv.abst.distort.PointDeformKeyPoints;
import boofcv.alg.distort.ImageDistort;
import boofcv.alg.distort.PointToPixelTransform_F32;
import boofcv.alg.interpolate.InterpolationType;
import boofcv.core.image.border.BorderType;
import boofcv.factory.distort.FactoryDistort;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import georegression.struct.point.Point2D_F32;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ExamplePointDeformKeyPoints {
    public static void main(String[] args) {
        BufferedImage orig = UtilImageIO.loadImage("d:/repository/java/panocube/src/main/resources/grid.jpg");
        BufferedImage bufferedOut = new BufferedImage(orig.getWidth(),orig.getHeight(),BufferedImage.TYPE_INT_RGB);

        Planar<GrayF32> input = ConvertBufferedImage.convertFrom(orig,true, ImageType.pl(3,GrayF32.class));
        Planar<GrayF32> output = input.createSameShape();

        List<Point2D_F32> src = new ArrayList<Point2D_F32>();
        List<Point2D_F32> dst = new ArrayList<Point2D_F32>();

        src.add(new Point2D_F32(100, 100));
        src.add(new Point2D_F32(100, 200));
        src.add(new Point2D_F32(200, 200));
        src.add(new Point2D_F32(200, 100));

        dst.add(new Point2D_F32(050, 050));
        dst.add(new Point2D_F32(050, 250));
        dst.add(new Point2D_F32(250, 250));
        dst.add(new Point2D_F32(250, 050));


        ConfigDeformPointMLS config = new ConfigDeformPointMLS();
        config.cols = 250;
        PointDeformKeyPoints deform = FactoryDistort.deformMls(config);
        deform.setImageShape(input.width,input.height);


        ImageDistort<Planar<GrayF32>,Planar<GrayF32>> distorter =
                FactoryDistort.distort(true, InterpolationType.BILINEAR,
                        BorderType.ZERO, input.getImageType(), input.getImageType());

        deform.setImageShape(input.width,input.height);
        deform.setSource(src);

        //ConvertBufferedImage.convertTo(output, bufferedOut, true);
        //ImagePanel panel = ShowImages.showWindow(bufferedOut,"Point Based Distortion Animation", true);

        // tell the deformation algorithm that destination points have changed
        deform.setDestination(dst);
        // Tell the distorter that the model has changed. If cached is set to false you can ignore this step
        distorter.setModel( new PointToPixelTransform_F32(deform));
        // distort the image
        distorter.apply(input, output);
        // Show the results
        ConvertBufferedImage.convertTo(output, bufferedOut, true);
        //panel.repaint();

        Graphics2D g2 = bufferedOut.createGraphics();
        g2.setColor(Color.RED);
        for (Point2D_F32 pts : src) {
            g2.drawArc(
                    (int) (pts.getX() - 2),
                    (int) (pts.getY() - 2),
                    4, 4, 0, 360);
        }

        g2.setColor(Color.GREEN);
        for (Point2D_F32 ptd : dst) {
            g2.drawArc(
                    (int) (ptd.getX() - 2),
                    (int) (ptd.getY() - 2),
                    4, 4, 0, 360);
        }

        UtilImageIO.saveImage(bufferedOut, "d:/repository/java/panocube/src/main/resources/demo_test.jpg");

    }
}