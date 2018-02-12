package measure.distance;


import measure.Measure;

public class EuclideanDistance extends Measure{

	
	@Override
	public double compute(final double[] p1, final double[] p2, final double[] weight) {
		//BigDecimal bd = BigDecimal.valueOf(0);
		double r = 0;
		for (int k=0; k<p1.length; k++){
			//bd = bd.add(BigDecimal.valueOf(Math.pow(p2[k] - p1[k], 2)));
			if (weight == null){
				r += Math.pow(p2[k] - p1[k], 2);
			}else{
				r += Math.pow(p2[k] - p1[k], 2)*weight[k];
			}
		}
		//return Math.sqrt(bd.doubleValue()); ]
		return Math.sqrt(r);
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
