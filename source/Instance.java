package dataset;

import java.util.ArrayList;

import measure.Measure;

public abstract class Instance {
	private ArrayList<Integer> categoricalAttributes = null;

	
	public abstract void setLabel(double value);
	public abstract double getLabel();
	public abstract double[] getPosition();
	public abstract double getPositionOnAxis(int axisIndex);
	public abstract int size();
	public abstract void print();
	
	public boolean isCategorical(final int attributeIndex){
		return (categoricalAttributes.contains(attributeIndex));
	}
	public void setCategoricalAttributeIndices(ArrayList<Integer> categoricalAttributes){
		this.categoricalAttributes = categoricalAttributes;
	}
	public ArrayList<Integer> getCategoricalAttributeIndices(){return this.categoricalAttributes;}
	public boolean hasCategoricalValues(){if (categoricalAttributes == null) return false; return this.categoricalAttributes.size() > 0;}
	
	public double computeMeasure(final Instance i, final Measure measure){
		return measure.compute(this, i, true);
	}
}
