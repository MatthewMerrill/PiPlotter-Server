package mattmerr47.piplot.render;

public class Stepper {
	
	private Easel easel;
	private double stringLength = -1;
	
	public Stepper(Easel easel){
		this.easel = easel;
	}
	
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
