package mattmerr47.piplot;

public interface ITracer {
	
	public boolean getMarking();
	public void setMarking(boolean marking);
	
	public void hoverTo(double[] to);
	
	public void line(double[] from, double[] to);
	public void bezier(double[] from, double[] to, double[][] controls);
	public void circle(double[] center, double radius);
	public void ellipse(double[] center, double radiusH, double radiusV);
	
}
