package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ElevatorSubsystem;

public class ElevatorCommand extends Command{
    public enum Position{ZERO, L1, L2, L3, L4, SUBSTATION}
    private ElevatorSubsystem subsystem;
    private Position pose;

    public ElevatorCommand(ElevatorSubsystem subsystem, Position pose){
        this.pose = pose;
        this.subsystem = subsystem;

        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
        switch (pose) {
            case ZERO:
            subsystem.runHeight(0);
                break;
        
            case L1:
            subsystem.runHeight(5);
                break;

            case L2:
            subsystem.runHeight(10);
                break;

            case L3:
            subsystem.runHeight(15);
                break;

            case L4:
            subsystem.runHeight(20);
                break;

            case SUBSTATION:
            subsystem.runHeight(25);
                break;
        
            default:
                break;
        }
    }

    @Override
    public void end(boolean interrupted){
        subsystem.stopElevator();
    }

    @Override
    public boolean isFinished(){
       return subsystem.atSetpoint();
    }
}