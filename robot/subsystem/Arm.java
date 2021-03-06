package frc.robot.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
// import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Arm extends SubsystemBase{
    public CANSparkMax armMotor;
    public RelativeEncoder armEncoder;

    double power;
    double powerFactor;
    double maxArmEncoderValue = 20.5;

    private boolean xWasPressed;
    private boolean yWasPressed;
    private boolean aWasPressed;

    private static Arm instance = null;
    
    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    public Arm() {
        armMotor = new CANSparkMax(10, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();
    }
    
    public void resetEncoderValues(){
        armEncoder.setPosition(0);
    }
    
    public void stop() {
        armMotor.set(0);
    }

    public void armPeriodic() {
        updateArmEncoderValue();

        powerFactor = 0.625;
        power = -OI.getInstance().armController.getRawAxis(5) * powerFactor;
       

        if (OI.getInstance().armController.getRawButton(5)) {
            power = -0.1;
        } else if (armEncoder.getPosition() <= 4 && armEncoder.getPosition() > 0) {
            if (power < 0) {
                powerFactor = 0.025;
            }
        } else if (armEncoder.getPosition() <= 0) {
            if (power < 0) {
                powerFactor = 0;
            }
        } else if (armEncoder.getPosition() >= maxArmEncoderValue) {
            if (power > 0) {
                power = 0;
            }
        }

        armMotor.set(power);
    }

    public void updateArmEncoderValue() {
        if (OI.getInstance().armController.getRawButton(4)) {
            if (yWasPressed == false) {
                yWasPressed = true;
                maxArmEncoderValue += 0.5;
            }
        } else {
            yWasPressed = false;
        }


        if (OI.getInstance().armController.getRawButton(1)) {
            if (aWasPressed == false) {
                aWasPressed = true;
                maxArmEncoderValue -= 0.5;
            }
        } else {
            aWasPressed = false;
        }


        if (OI.getInstance().armController.getRawButton(3)) {
            if (xWasPressed == false) {
                xWasPressed = true;
                resetEncoderValues();
            }
        } else {
            xWasPressed = false;
        }
    }
}