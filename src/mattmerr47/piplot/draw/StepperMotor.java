package mattmerr47.piplot.draw;

import mattmerr47.piplot.io.Logger;
import mattmerr47.piplot.io.Logger.LEVEL;
import mattmerr47.piplot.io.plotter.IStepperMotor;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class StepperMotor implements IStepperMotor {
	
	final static GpioController gpio = GpioFactory.getInstance();
	
	public final static GpioPinDigitalOutput[] pins2 = {
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
	
	public final static GpioPinDigitalOutput[] pins1 = {
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW),
		gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW)};
	
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
		(byte) 0b1001,};
	
	private final GpioStepperMotorComponent step; 
	private int stepsMoved = 0;
	
	public StepperMotor(int stepsPerRevolution, byte[] stepSequence, GpioPinDigitalOutput[] pins) {

		gpio.setShutdownOptions(true, PinState.LOW, pins);
		step = new GpioStepperMotorComponent(pins);
		
		step.setStepInterval((long) 4);
		
		step.setStepSequence(stepSequence);
		step.setStepsPerRevolution(stepsPerRevolution);
		
	}
	
	@Override
	public double getStepsPerRev() {
		return step.getStepsPerRevolution();
	}
	
	@Override
	public int getStepsMoved() {
		return stepsMoved;
	}
	
	@Override
	public void turn(double degrees, double stepInterval) {
		
		try {
			
			long delayMS = (long) (stepInterval / 1);
			int delayNS = (int) ((stepInterval - delayMS) * 1000000);
			
			Logger.print(LEVEL.DEBUG_FINE, stepInterval + " -> " + ((long)stepInterval) +  ", " + delayNS);
			Logger.print(LEVEL.DEBUG_FINE, "degrees:"+ degrees);
			
			step.setStepInterval(delayMS, delayNS);
			step.rotate(degrees/360);
		} catch (Exception e) {
			e.printStackTrace();
			step.setStepInterval((long)stepInterval);
			step.rotate(degrees/360);
		}

        long steps = Math.round(getStepsPerRev() * (degrees/360));
        stepsMoved += steps;
	}
}
