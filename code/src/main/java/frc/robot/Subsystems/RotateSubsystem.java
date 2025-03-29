package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstats;

public class RotateSubsystem extends SubsystemBase{
    protected TalonFX mRotMotor;

    private double zero = 0.0;

    public RotateSubsystem(){
        mRotMotor = new TalonFX(IntakeConstats.kRotMotorID);

        TalonFXConfiguration intakeRot = new TalonFXConfiguration();
        intakeRot.Feedback.SensorToMechanismRatio = 9;
        intakeRot.MotionMagic.MotionMagicAcceleration = 250;
        intakeRot.MotionMagic.MotionMagicCruiseVelocity = 1000;
        intakeRot.MotionMagic.MotionMagicExpo_kA = 0.01;
        intakeRot.MotionMagic.MotionMagicJerk = 0.05;

        mRotMotor.getConfigurator().apply(intakeRot);
    }

    public void rotate(double pos){
        mRotMotor.setControl(new MotionMagicVoltage(pos / (2 * Math.PI)));
    }

    public void zeroRotate(){
        mRotMotor.setPosition(0);
        zero = mRotMotor.getPosition().getValueAsDouble();
    }

    public void stopRotate(){
        mRotMotor.set(0);
    }
}
