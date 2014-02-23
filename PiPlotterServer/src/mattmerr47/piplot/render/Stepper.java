package mattmerr47.piplot.render;

public class Stepper {
	
	private double stringLength = -1;
	
	public void setLength(double length){
		this.stringLength = length;
	}
	public double getLength(){
		return this.stringLength;
	}
	
	public void changeLengthBy(double dLength){
		setLength( getLength() + dLength );
	}

}
