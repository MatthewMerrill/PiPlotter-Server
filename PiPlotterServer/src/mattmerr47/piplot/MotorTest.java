package mattmerr47.piplot;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class MotorTest {
	
    final static GpioController gpio = GpioFactory.getInstance();
    final static GpioPinDigitalOutput[] pins1 = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
    final static GpioPinDigitalOutput[] pins2 = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW)};
    final static GpioPinDigitalOutput[] pins3 = {
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
        
        //new PCA9685GpioServoProvider(null).;
       // servo = new GenericServo(pins3);
        
        //  This sequence requires the least amount of energy and generates the smoothest movement.)
        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;  
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;
 
        // create byte array to demonstrate a double-step sequencing
        // (In this method two coils are turned on simultaneously.  This method does not generate
        //  a smooth movement as the previous method, and it requires double the current, but as
        //  return it generates double the torque.)
        byte[] double_step_sequence = new byte[4];
        double_step_sequence[0] = (byte) 0b0011;  
        double_step_sequence[1] = (byte) 0b0110;
        double_step_sequence[2] = (byte) 0b1100;
        double_step_sequence[3] = (byte) 0b1001;
       
        // create byte array to demonstrate a half-step sequencing
        // (In this method two coils are turned on simultaneously.  This method does not generate
        //  a smooth movement as the previous method, and it requires double the current, but as
        //  return it generates double the torque.)
        byte[] half_step_sequence = new byte[8];
        half_step_sequence[0] = (byte) 0b0001;  
        half_step_sequence[1] = (byte) 0b0011;
        half_step_sequence[2] = (byte) 0b0010;
        half_step_sequence[3] = (byte) 0b0110;
        half_step_sequence[4] = (byte) 0b0100;
        half_step_sequence[5] = (byte) 0b1100;
        half_step_sequence[6] = (byte) 0b1000;
        half_step_sequence[7] = (byte) 0b1001;
 
        // define stepper parameters before attempting to control motor
        // anything lower than 2 ms does not work for my sample motor using single step sequence
        step1.setStepInterval((long) 4);  
        step2.setStepInterval((long) 4);
        step1.setStepSequence(single_step_sequence);
        step2.setStepSequence(single_step_sequence);
 
        // There are 32 steps per revolution on my sample motor,
        // and inside is a ~1/64 reduction gear set.
        // Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
        // This means is that there are really 32*63.683950617 steps per revolution
        // =  2037.88641975 ~ 2038 steps!
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
