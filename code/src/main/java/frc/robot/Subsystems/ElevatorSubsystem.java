package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    
    protected TalonFX mElevatorLeft;
    protected TalonFX mElevatorRight;

    public final Follower follow;

    private int Setpoint = 0;

    public ElevatorSubsystem(){
        mElevatorLeft = new TalonFX(ElevatorConstants.kElevatorLeftID);
        mElevatorRight = new TalonFX(ElevatorConstants.kElevatorRightID);

        TalonFXConfiguration left = new TalonFXConfiguration();
        TalonFXConfiguration right = new TalonFXConfiguration();

        left.MotorOutput.Inverted = left.MotorOutput.Inverted.Clockwise_Positive;

        left.MotionMagic.MotionMagicCruiseVelocity = 3000;
        right.MotionMagic.MotionMagicCruiseVelocity = 3000;
        left.MotionMagic.MotionMagicAcceleration = 500;
        right.MotionMagic.MotionMagicAcceleration = 500;

        left.Feedback.SensorToMechanismRatio = 25;
        right.Feedback.SensorToMechanismRatio = 25;

        mElevatorLeft.getConfigurator().apply(left);
        mElevatorRight.getConfigurator().apply(right);

        follow = new Follower(ElevatorConstants.kElevatorLeftID, true);
    }

    public void runHeight(int height){
        Setpoint = height;

        mElevatorLeft.setControl(new MotionMagicVoltage(height));
        mElevatorRight.setControl(follow);
    }

    public boolean atSetpoint(){
        return getHeight() >= Setpoint ? true : false;
    }

    public double getHeight(){
        return Setpoint;
    }

    public void zeroElevator(){

    }

    public void stopElevator(){
        mElevatorLeft.set(0);
        mElevatorRight.set(0);
    }

    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Elevator At Setpoint", atSetpoint());
        SmartDashboard.putNumber("Elevator Current Height", getHeight());
    }
}
