package mattmerr47.piplot;

import mattmerr47.piplot.draw.StepperMotor;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class MotorTest {
	
    final static GpioController gpio = GpioFactory.getInstance();
    public final static GpioPinDigitalOutput[] pins1 = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
    public final static GpioPinDigitalOutput[] pins2 = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW)};
    public final static GpioPinDigitalOutput[] pins3 = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, PinState.LOW)};
    
    private static GpioStepperMotorComponent step1;    
    private static GpioStepperMotorComponent step2;
    //GenericServo servo;
    
    public static void init() {
		gpio.setShutdownOptions(true, PinState.LOW, pins1);
		gpio.setShutdownOptions(true, PinState.LOW, pins2);
		gpio.setShutdownOptions(true, PinState.LOW, pins3);
		
        step1 = new GpioStepperMotorComponent(pins1);
        step2 = new GpioStepperMotorComponent(pins2);
        
        step1.setStepInterval((long) 4);
        step2.setStepInterval((long) 4);
        step1.setStepSequence(StepperMotor.SINGLE_STEP_SEQUENCE);
        step2.setStepSequence(StepperMotor.SINGLE_STEP_SEQUENCE);
        
        step1.setStepsPerRevolution(2048);
        step2.setStepsPerRevolution(2048);
        
    }
    
	public static void main(String[] args) {

		init();
		new Thread(new Runnable(){

			@Override
			public void run() {
				step1.rotate(3);
			}}).start();
		
		step2.rotate(-3);
		
		
	}

}
