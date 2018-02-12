package measure.distance;

import measure.Measure;

public class MinkowskiDistance extends Measure{
	private float p;
	
	public MinkowskiDistance(float p){
		this.p = p;
	}

	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		double r = 0;
		for (int k=0; k<p1.length; k++){
			if (weight == null)
				r += Math.pow(Math.abs(p2[k] - p1[k]), p);
			else
				r += Math.pow(Math.abs(p2[k] - p1[k]), p)*weight[k];
		}
		return Math.pow(r, 1/(float)p);
	}

	@Override
	public String getName() {
		return this.getClass().getName() + "[p: " + p + "]";
	}
}