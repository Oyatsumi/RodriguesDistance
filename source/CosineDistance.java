package measure.distance;

import measure.Measure;

public class CosineDistance extends Measure{


	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		double top = 0, bottom1 = 0, bottom2 = 0;
		for (int k=0; k<p1.length; k++){
			if (weight == null){
				top += (p2[k] * p1[k]);
				bottom1 += Math.pow(p2[k], 2);
				bottom2 += Math.pow(p1[k], 2);
			}else{
				top += (p2[k] * p1[k])*weight[k];
				bottom1 += Math.pow(p2[k], 2)*weight[k];
				bottom2 += Math.pow(p1[k], 2)*weight[k];
			}
		}
		return 1 - (top)/(bottom1*bottom2);
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
