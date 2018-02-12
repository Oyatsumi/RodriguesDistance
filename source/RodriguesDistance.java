package measure.distance;

import measure.Measure;

public class RodriguesDistance extends Measure{
	private float p = 1;
	
	public RodriguesDistance(){
		
	}
	
	public RodriguesDistance(final float p){
		this.p = p;
	}

	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		double r = 0, max = 0;
		for (int k=0; k<p1.length; k++){
			if (weight == null){
				if (max < Math.abs(p2[k] - p1[k]))
					max = Math.abs(p2[k] - p1[k]);
				r += Math.pow(Math.abs(p2[k] - p1[k]), p);
			}else{
				if (max < Math.abs(p2[k] - p1[k]))
					max = Math.abs(p2[k] - p1[k])*weight[k];
				r += Math.pow(Math.abs(p2[k] - p1[k]), p)*weight[k];
			}
		}
		return max + Math.pow(r, 1f/p);
	}

	@Override
	public String getName() {
		return this.getClass().getName() + "[p: " + p + "]";
	}
}