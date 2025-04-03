package frc.robot.Commands;

import static edu.wpi.first.units.Units.*;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.PivotSubsystem;

public class PivotCommand  extends Command{
    public static enum Positions{ZERO, L1, L23, L4, GROUND, SUBSTATION}
    private Positions pose;
    private Rotation2d target;
    PivotSubsystem subsystem;

    public PivotCommand(PivotSubsystem subsystem, Positions pose){
        this.subsystem = subsystem;
        this.pose = pose;
        addRequirements(subsystem);
    }

     @Override
    public void initialize() {
       switch (pose) {

        case L1 :
            target = new Rotation2d(Units.degreesToRadians(25));
            subsystem.pivot(target);
            break;
        
        case L23 :
        target = new Rotation2d(Units.degreesToRadians(40));
        subsystem.pivot(target);
            break;

        default:
            subsystem.stopPivot();
            break;
       }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopPivot();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

}
