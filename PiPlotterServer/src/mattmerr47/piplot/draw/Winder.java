package mattmerr47.piplot.draw;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Winder {
	
	final GpioStepperMotorComponent stepper;
	
	/**
	 * Maximum string length in centimeters.
	 */
	private final double maxLength;
	private final double position;
	
	public Winder(GpioPinDigitalOutput[] pins, double maxLength, double position) {
		stepper = new GpioStepperMotorComponent(pins);
		this.maxLength = maxLength;
		this.position = position;
	}

}
