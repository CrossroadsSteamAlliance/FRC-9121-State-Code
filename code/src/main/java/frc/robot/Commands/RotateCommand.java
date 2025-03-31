package frc.robot.Commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.RotateSubsystem;

public class RotateCommand  extends Command{
    public static enum Position{ZERO, L, R}
    private Position pose;
    RotateSubsystem subsystem;
    private Rotation2d rotatePose;

    public RotateCommand(RotateSubsystem subsystem, Position pose){
        this.subsystem = subsystem;
        this.pose = pose;
        addRequirements(subsystem);
    }

     @Override
    public void initialize() {
       switch (pose) {

        case ZERO:
            rotatePose = new Rotation2d(90);
            break;

        case L:
            rotatePose = new Rotation2d(180);
            break;

        case R:
            rotatePose = new Rotation2d(0);
            break;

        default:
            subsystem.stopRotate();
            break;
       }

       subsystem.rotate(rotatePose);
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
