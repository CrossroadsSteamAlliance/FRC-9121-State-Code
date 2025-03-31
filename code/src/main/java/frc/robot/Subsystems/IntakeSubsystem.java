package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstats;

public class IntakeSubsystem extends SubsystemBase {

    private SimpleMotorFeedforward feeds;
    
    protected TalonFX mTopMotor;
    protected TalonFX mBottomMotor;

    public IntakeSubsystem(){
        mTopMotor = new TalonFX(IntakeConstats.kTopMotorID);
        mBottomMotor = new TalonFX(IntakeConstats.kBottomMotorID);

        TalonFXConfiguration intakeTop = new TalonFXConfiguration();
        TalonFXConfiguration intakeBottom = new TalonFXConfiguration();
        intakeTop.MotorOutput.Inverted = intakeTop.MotorOutput.Inverted.CounterClockwise_Positive;
        intakeBottom.MotorOutput.Inverted = intakeBottom.MotorOutput.Inverted.Clockwise_Positive;

        mTopMotor.getConfigurator().apply(intakeTop);
        mBottomMotor.getConfigurator().apply(intakeBottom);
        
        feeds = new SimpleMotorFeedforward(0.5, 1);
    }

    public void intake(double velocity){
        mTopMotor.setControl(new DutyCycleOut(feeds.calculate(velocity)));
        mTopMotor.setControl(new DutyCycleOut(feeds.calculate(velocity)));
    }

    public void outtake(double velocity){
        mTopMotor.setControl(new DutyCycleOut(-feeds.calculate(velocity)));
        mTopMotor.setControl(new DutyCycleOut(-feeds.calculate(velocity)));
    }


    public void stopIntake(){
        mBottomMotor.set(0);
        mTopMotor.set(0);
    }

    public void stop(){
        stopIntake();
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Intake Active", mTopMotor.get() > 0 || mTopMotor.get() < 0);
        SmartDashboard.putNumber("Intake Speed", mBottomMotor.get());
    }
}
