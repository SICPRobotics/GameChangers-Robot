// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Hanger;

// public final class ExtendHangerArm extends CommandBase {
//     private final Hanger hanger;

//     public ExtendHangerArm(final Hanger hanger) {
//         this.hanger = hanger;
//         addRequirements(hanger);
//     }

//     @Override
//     public void initialize() {
//         this.hanger.startArmExtension();
//     }

//     @Override
//     public boolean isFinished() {
//         return this.hanger.getArmDistance() > 100;
//     }

//     @Override
//     public void end(final boolean interrupted) {
//         this.hanger.stopArmExtension();
//     }
// }