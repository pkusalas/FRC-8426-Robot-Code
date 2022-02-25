package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CommandGroup extends SequentialCommandGroup{
    public CommandGroup() {
        addCommands(
            new DriveCommand(40),
            new TurnCommand(24)
        );
    }
}