package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstats;

public class PivotSubsystem extends SubsystemBase{
    protected TalonFX mPivotLeftMotor;
    protected TalonFX mPivotRightMotor;
    
    private double Setpoint;
    public PivotSubsystem(){
        mPivotLeftMotor = new TalonFX(IntakeConstats.kPivotLeftId);
        mPivotRightMotor = new TalonFX(IntakeConstats.kPivotRightId);

        TalonFXConfiguration intakePLeft = new TalonFXConfiguration();
        TalonFXConfiguration intakePRight = new TalonFXConfiguration(); 

        intakePLeft.MotorOutput.Inverted = intakePLeft.MotorOutput.Inverted.CounterClockwise_Positive;
        intakePRight.MotorOutput.Inverted = intakePRight.MotorOutput.Inverted.Clockwise_Positive;
        intakePLeft.Feedback.SensorToMechanismRatio = 10;
        intakePRight.Feedback.SensorToMechanismRatio = 10;

        mPivotLeftMotor.getConfigurator().apply(intakePLeft);
        mPivotRightMotor.getConfigurator().apply(intakePRight);
    }

       public void pivot(double pos){
        mPivotLeftMotor.setControl(new MotionMagicVoltage(pos));
        mPivotRightMotor.setControl(new MotionMagicVoltage(pos));
    }

    public void stopPivot(){
        mPivotLeftMotor.set(0);
        mPivotRightMotor.set(0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Setpoint", Setpoint);
        SmartDashboard.putNumber("Current Position", mPivotLeftMotor.getPosition().getValueAsDouble());
    }
}
