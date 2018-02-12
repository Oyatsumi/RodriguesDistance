package measure.distance;

import measure.Measure;

public class CanberraDistance extends Measure{


	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		double r = 0;
		for (int k=0; k<p1.length; k++){
				if (weight == null)
					r += Math.abs(p2[k] - p1[k])/(Math.abs(p2[k]) + Math.abs(p1[k]));
				else
					r += (Math.abs(p2[k] - p1[k])/(Math.abs(p2[k]) + Math.abs(p1[k]))) *weight[k];
		}
		return r;
	}

	
}