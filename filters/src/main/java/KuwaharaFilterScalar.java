import boofcv.struct.image.GrayF;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;

public class KuwaharaFilterScalar {

    private final int n;			// fixed subregion size
    private final int dm;			// = d-
    private final int dp;			// = d+
    private final float tSigma;

    public KuwaharaFilterScalar(int radius, float tSigma) {
        int r = radius;
        this.n = (r + 1) * (r + 1);		// size of complete filter
        this.dm = (r / 2) - r;			// d- = top/left center coordinate
        this.dp = this.dm + r;			// d+ = bottom/right center coordinate
        this.tSigma = tSigma;
    }

    private float sMin;		// min. variance
    private float aMin;

    protected float doPixel(GrayF32 plane, int u, int v) {
        sMin = Float.MAX_VALUE; aMin= 0;
        evalSubregionGray(plane, u, v);		// a centered subregion (not in original Kuwahara)
        sMin = sMin - tSigma * n;				// tS * n because we use variance scaled by n
        evalSubregionGray(plane, u + dm, v + dm);
        evalSubregionGray(plane, u + dm, v + dp);
        evalSubregionGray(plane, u + dp, v + dm);
        evalSubregionGray(plane, u + dp, v + dp);

        return aMin;
    }
    // sets the member variables Smin, Amin
    private void evalSubregionGray(GrayF32 source, int u, int v) {
        if (!source.isInBounds(u,v)) {
            return;
        }
        float S1 = 0;
        float S2 = 0;
        for (int j = dm; j <= dp; j++) {
            for (int i = dm; i <= dp; i++) {
                if (!source.isInBounds(u+i, v+j)) {
                    continue;
                }
                float a = source.getIndex(u+i, v+j);
                S1 = S1 + a;
                S2 = S2 + a * a;
            }
        }
//		double s = (sum2 - sum1*sum1/n)/n;	// actual sigma^2
        float s = S2 - S1 * S1 / n;			// s = n * sigma^2
        if (s < sMin) {
            sMin = s;
            aMin = S1 / n; // mean
        }
    }
}
