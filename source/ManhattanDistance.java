package measure.distance;

public class ManhattanDistance extends MinkowskiDistance{

	public ManhattanDistance() {
		super(1);
	}
	
	@Override
	public String getName() {
		return this.getClass().getName();
	}
	
}
