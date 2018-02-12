package measure;

import dataset.Instance;
import measure.distance.ChebyshevDistance;
import measure.distance.EuclideanDistance;

public abstract class Measure {
	public static final Measure CHEBYSHEV_DISTANCE = new ChebyshevDistance(), EUCLIDEAN_DISTANCE = new EuclideanDistance(), ITERATED_DISTANCE_2D = new IteratedDistance2D();

	public abstract double compute(double[] p1, double[] p2, double wieght[]);
	
	public double compute(final double p1[], final double[] p2){
		return compute(p1, p2, null);
	}
	
	public abstract String getName();
	
	public double compute(Instance i1, Instance i2) {
		return compute(i1.getPosition(), i2.getPosition());
	}
	
	public double compute(Instance i1, Instance i2, boolean useCategoricalValues) { //for knn categorical values
		if (!useCategoricalValues || !i1.hasCategoricalValues()) return compute(i1, i2);
		
		double[] origP1 = i1.getPosition(),
				origP2 = i2.getPosition();
		double[] p1 = new double[origP1.length - i1.getCategoricalAttributeIndices().size()],
				p2 = new double[p1.length];
		int i = 0;
		for (int k=0; k<p1.length; k++){
			if (i1.isCategorical(i)){
				i++;
				k--;
				continue;
			}
			p1[k] = origP1[i];
			p2[k] = origP2[i];
			i++;
		}
		double distance = compute(p1, p2);
		
		for (int k=0; k<origP1.length; k++){
			if (i1.isCategorical(k))
				if (i1.getPositionOnAxis(k) == i2.getPositionOnAxis(k))
					distance++;
		}
		return distance;
	}
	
}
