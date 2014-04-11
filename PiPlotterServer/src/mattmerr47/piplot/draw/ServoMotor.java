package mattmerr47.piplot.draw;

import java.math.BigDecimal;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class ServoMotor {
	
	private final GpioController gpio = GpioFactory.getInstance();

	private GpioPinDigitalOutput servo;
	private boolean marking = false;
	
	public ServoMotor() {
		servo = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "Lift Servo", PinState.LOW);
	}
	
	private int[] convertMillis(double time){
		int delayMS = (int) (time / 1);
		int delayNS = (int) ((time - delayMS) * 1000000);
		return new int[]{delayMS, delayNS};
	}
	
	/*
	 * TODO: Not functional yet - Still debugging and finding pulse width for desired angles.
	 */
	public void setMarking(boolean marking) throws InterruptedException {
		
		if (this.marking == marking)
			return;
		
		BigDecimal high = new BigDecimal((marking)?1:2);
		BigDecimal low = new BigDecimal(20).subtract(high);
		
		int[] highs = convertMillis(high.doubleValue());		
		int[] lows = convertMillis(low.doubleValue());
		
		for (int i = 0; i < 100; i++) {
			servo.high();
			//servo.setState(PinState.HIGH);
			Thread.sleep(highs[0], highs[1]);
			servo.low();
			//servo.setState(PinState.LOW);
			Thread.sleep(lows[0], highs[1]);
		}
		
		Thread.sleep(1000);
		this.marking = marking;
	}
	
	public boolean getMarking() {
		return servo.getState() == PinState.HIGH;
	}
	
}
