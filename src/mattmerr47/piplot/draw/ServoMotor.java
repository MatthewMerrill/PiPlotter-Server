package mattmerr47.piplot.draw;

import java.io.IOException;
import java.math.BigDecimal;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class ServoMotor {
	
	private GpioController gpio = GpioFactory.getInstance();

	private GpioPinDigitalOutput servo;
	private boolean marking = false;
	
	public void clear() {
		gpio.removeAllListeners();
		gpio.removeAllTriggers();
		
		gpio.unprovisionPin(servo);
	}
	
	public ServoMotor() {
		for (Object o : gpio.getProvisionedPins().toArray())
			gpio.unprovisionPin((GpioPin)o);
			
		servo = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "Lift Servo", PinState.LOW);
		servo.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}
	
	private int[] convertMillis(double time){
		int delayMS = (int) (time / 100.0);
		int delayNS = (int) (((time/100.0) - delayMS) * 1000000);
		return new int[]{delayMS, delayNS};
	}
	
	/*
	 * TODO: Not functional yet - Still debugging and finding pulse width for desired angles.
	 */
	public void setMarking(boolean marking) throws InterruptedException, IOException {
		
		if (this.marking == marking)
			return;

		BigDecimal high = new BigDecimal((marking)?249.99:50);
		//BigDecimal low = new BigDecimal(2000).subtract(high);
		
		int[] highs = convertMillis(high.doubleValue());		
		//int[] lows = convertMillis(low.doubleValue());
		
		for (int i = 0; i < 100; i++) {
			servo.high();
			//servo.setState(PinState.HIGH);
			Thread.sleep(highs[0], highs[1]);
			servo.low();
			//servo.setState(PinState.LOW);
			Thread.sleep(20-highs[0], (1000000-highs[1])%1000000);
		}
		
		//Thread.sleep(1000);
		this.marking = marking;
	}
	
	public boolean getMarking() {
		return servo.getState() == PinState.HIGH;
	}
	
}
