/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.plaf.TextUI;

import com.ctre.phoenix.motion.TrajectoryPoint;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Gate;
import frc.robot.Constants.GroundIntake;
import frc.robot.Constants.Hanger;
import frc.robot.Constants.PastaPuller;
import frc.robot.commands.DoNothing;
// import frc.robot.commands.AutonomusCommand;
// import frc.robot.commands.Calibrate;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.ExtendPiston;
import frc.robot.commands.FlyWheelCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.NudgeMotor;
import frc.robot.commands.ResetPoistion;
import frc.robot.commands.TurretTurn;
// import frc.robot.commands.SetLightsToColor;
// import frc.robot.commands.color_wheel.SpinNumberOfTimes;
// import frc.robot.commands.color_wheel.SpinToColor;
import frc.robot.controllers.OperatorController;
// import frc.robot.subsystems.Cameras;
// import frc.robot.subsystems.ColorWheelPiston;
// import frc.robot.subsystems.Compessor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FlyWheel;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TrajectoryGeneration;
import frc.robot.subsystems.Turret;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public final class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Joystick joystick = new Joystick(0);
  private final OperatorController operatorController = new OperatorController(1);
  private final DriveTrain driveTrain;
  private final TrajectoryGeneration trajectoryGeneration;
  private final Intake intake;
  private final FlyWheel flyWheel;
  private final Turret turret;
  private final JoystickButton thumbButton;
  private final JoystickButton twelveButton;
  private final JoystickButton trigger;
  private final JoystickButton threeButton;
  private final JoystickButton five;
  private final JoystickButton six;
  // private final Cameras cameras;
  // private final Lights lights;
  // private final RightWinch rightWinch;
  // private final LeftWinch leftWinch;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    driveTrain = new DriveTrain();
    intake = new Intake();
    flyWheel = new FlyWheel();
    turret = new Turret();
    trajectoryGeneration = new TrajectoryGeneration(driveTrain.getPose(),
        new Pose2d(new Translation2d(0, 2), new Rotation2d(Math.PI/2)), 
        List.of(new Translation2d(-1,1)), driveTrain);
    driveTrain.setDefaultCommand(
        new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust));
    thumbButton = new JoystickButton(joystick, 2);
    twelveButton = new JoystickButton(joystick, 12);
    trigger = new JoystickButton(joystick, 1);
    threeButton = new JoystickButton(joystick, 3);
    five = new JoystickButton(joystick, 5);
    six = new JoystickButton(joystick, 6);
    // Configure the button bindings
    configureButtonBindings();
    SmartDashboard.putNumber("Auton Chooser", 0);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    thumbButton.toggleWhenPressed(
        new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust, true));
    twelveButton.toggleWhenPressed(new ResetPoistion(driveTrain));
    trigger.toggleWhenPressed(new IntakeCommand(intake));
    threeButton.toggleWhenPressed(new FlyWheelCommand(flyWheel));
    five.whenPressed(new TurretTurn(turret, 1));
    six.whenPressed(new TurretTurn(turret, -1));
  }
  
  public double getJoystickX() {
    return this.joystick.getRawAxis(Constants.Joystick.X_AXIS);
  }

  public double getJoystickY() {
    return this.joystick.getRawAxis(Constants.Joystick.Y_AXIS);
  }

  public double getJoystickZ() {
    return this.joystick.getRawAxis(2);
  }

  public double getJoystickAdjust() {
    return this.joystick.getRawAxis(Constants.Joystick.ADJUST_AXIS);
  }
  public void generateTrajectory(){
    trajectoryGeneration.generateTrajectory();
  }
  // * @return the command to run in autonomous
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return new AutonomusCommand(driveTrain, gate, pastaPuller, hangerArm);
    //PLAN: subsystem to generate trajectory, brings in the tragectory into here and those paramiters, kiniatics is handledd by DriveTrain, 
    //Ramsete Command will be made Here and this method will just return that. 
    return new RamseteCommand(
    trajectoryGeneration.getTrajectory(),
     driveTrain::getPose,
      new RamseteController(),
       new SimpleMotorFeedforward(Constants.VoltageConstants.kS,Constants.VoltageConstants.kV,Constants.VoltageConstants.kA),
        driveTrain.kinematics,
         driveTrain::getWheelSpeeds,
         new PIDController(Constants.VoltageConstants.kP, 0, 0), 
          new PIDController(Constants.VoltageConstants.kP, 0, 0),
           driveTrain.tankDriveVolts,
            driveTrain);
  }
  
}