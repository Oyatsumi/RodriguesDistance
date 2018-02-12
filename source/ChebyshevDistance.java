package measure.distance;

import measure.Measure;

public class ChebyshevDistance extends Measure{


	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		double max = 0;
		double result = 0;
		for (int k=0; k<p1.length; k++){
			if (weight == null) 
				result = Math.abs(p2[k] - p1[k]);
			else
				result = Math.abs(p2[k] - p1[k])*weight[k];
			if (max < result) max = result;
		}
		return max;
	}


}
