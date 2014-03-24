package mattmerr47.piplot.draw;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class StepperMotor {
	
	/**
	 * Uses least energy and provides quick movement.
	 */
	public static final byte[] SINGLE_STEP_SEQUENCE = new byte[]{
		(byte) 0b0001,
		(byte) 0b0010,
		(byte) 0b0100,
		(byte) 0b1000};

	/**
	 * Uses twice as much energy as Single but provides more torque.
	 */
	public static final byte[] DOUBLE_STEP_SEQUENCE = new byte[]{
		(byte) 0b0011,
		(byte) 0b0110,
		(byte) 0b1100,
		(byte) 0b1001};

	/**
	 * Alternates steps of single and double.
	 * Uses average energy, has greatest torque, but takes twice as much time and steps to turn same distance. 
	 */
	public static final byte[] HALF_STEP_SEQUENCE = new byte[]{
		(byte) 0b0001,
		(byte) 0b0011,
		(byte) 0b0010,
		(byte) 0b0110,
		(byte) 0b0100,
		(byte) 0b1100,
		(byte) 0b1000,
		(byte) 0b1001};

    final static GpioController gpio = GpioFactory.getInstance();
	
    private final GpioStepperMotorComponent step; 
	
	public StepperMotor(int stepsPerRevolution, byte[] stepSequence, GpioPinDigitalOutput[] pins) {

		gpio.setShutdownOptions(true, PinState.LOW, pins);
        step = new GpioStepperMotorComponent(pins);
        
        step.setStepInterval((long) 4);
        
        step.setStepSequence(stepSequence);
        step.setStepsPerRevolution(stepsPerRevolution);
        
	}
	
	public void turn(double degrees, long stepInterval) {
		step.setStepInterval(stepInterval);
		step.rotate(degrees/360);
	}

}
