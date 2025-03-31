package frc.robot.Autonomous;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Autonomous extends Command {

    private SendableChooser<Command> autoChooser;

    public Autonomous(){
        autoChooser = AutoBuilder.buildAutoChooser();

        //autoChooser.addOption("Blue Mobility", blueMobilityAuton());
        //autoChooser.addOption("Red Mobility", redMobilityAuton());

        SmartDashboard.putData(autoChooser);
    }
    
    /*private Command blueMobilityAuton(){
        return drivetrain.applyRequest(()-> drive.withVelocityX(-0.6 * MaxSpeed)).withTimeout(3.5)
        .andThen(drivetrain.applyRequest(()-> drive.withVelocityX(0)));
    }

    private Command redMobilityAuton(){
        return drivetrain.applyRequest(()-> drive.withVelocityX(0.6 * MaxSpeed)).withTimeout(3.5)
        .andThen(drivetrain.applyRequest(()-> drive.withVelocityX(0)));
    }*/

    public Command getAutonomousCommand(){
        return autoChooser.getSelected();
    }
}
