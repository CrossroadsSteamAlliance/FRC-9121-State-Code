package frc.robot.Subsystems;

import org.dyn4j.geometry.Rotation;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstats;

public class RotateSubsystem extends SubsystemBase{
    private TalonFX mRotMotor;

    private double zero = 0.0;
    private Rotation2d p;

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

    public void rotate(Rotation2d pose){
        p = pose;
        mRotMotor.setControl(new MotionMagicVoltage(pose.getRotations()));
    }

    public void zeroRotate(){
        mRotMotor.setPosition(0);
        zero = mRotMotor.getPosition().getValueAsDouble();
    }

    public void stopRotate(){
        mRotMotor.set(0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Rotate Motor Zero", zero);
        SmartDashboard.putNumberArray("Setpoint || Current", new double[]{p.getRotations(), mRotMotor.getPosition().getValueAsDouble()});
    }
}
