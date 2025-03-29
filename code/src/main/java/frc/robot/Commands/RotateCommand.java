package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.RotateSubsystem;

public class RotateCommand  extends Command{
    public enum Position{ZERO, L, L4}
    private Position pose;
    RotateSubsystem subsystem;

    public RotateCommand(RotateSubsystem subsystem, Position pose){
        this.subsystem = subsystem;
        this.pose = pose;
        addRequirements(subsystem);
    }

     @Override
    public void initialize() {
       switch (pose) {

        case ZERO:
            subsystem.rotate(0);
            break;

        case L:
            subsystem.rotate(Math.toRadians(90));
            break;

        case L4:
            subsystem.rotate(Math.toRadians(180));
            break;

        default:
            subsystem.stopRotate();
            break;
       }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopRotate();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

}
