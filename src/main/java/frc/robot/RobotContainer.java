/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.Button;
// import frc.robot.commands.AutonomusCommand;
// import frc.robot.commands.Calibrate;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.KVCommand;
import frc.robot.commands.ResetPoistion;
import frc.robot.commands.VisionShoot;
// import frc.robot.commands.SetLightsToColor;
// import frc.robot.commands.color_wheel.SpinNumberOfTimes;
// import frc.robot.commands.color_wheel.SpinToColor;
import frc.robot.controllers.OperatorController;
import frc.robot.pi_client.PiClient;
// import frc.robot.subsystems.Cameras;
// import frc.robot.subsystems.ColorWheelPiston;
// import frc.robot.subsystems.Compessor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.FlyWheel;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.ShooterLights;
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
    private final Indexer indexer;
    private final Hood hood;
    private final Feeder feeder;
    private final ShooterLights shooterLights;
    private final JoystickButton thumbButton;
    private final JoystickButton twelveButton;
    private final JoystickButton trigger;
    private final JoystickButton threeButton;
    private final JoystickButton five;
    private final JoystickButton six;
    private final JoystickButton eleven;
    private final JoystickButton four;
    private final JoystickButton ten;
    private final JoystickButton nine;
    private final JoystickButton eight;
    private final PiClient pi = new PiClient();
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
        indexer = new Indexer();
        hood = new Hood();
        feeder = new Feeder();
        shooterLights = new ShooterLights();
        trajectoryGeneration = new TrajectoryGeneration(driveTrain.getPose(),
                new Pose2d(new Translation2d(0, 7)/* end (0, 0)*/ , new Rotation2d((Math.PI / 2))), 
            List.of( //0.762 per box
              new Translation2d(0, 1.25),
              new Translation2d(-1.5,1.25),
              new Translation2d(0, 1.5),
              new Translation2d(1, 1.75),
              new Translation2d(1.5, 3.7),
              new Translation2d(-1.8, 3.7),
              new Translation2d(1.5, 3.7),
              new Translation2d(1.5, 6),
              new Translation2d(-1.8, 6),
              new Translation2d(0, 6)
            //^^^^Bunce Cource
            ),
            //E6 (1.5, 3.7)
            //A6 (-1.8, 3.7)
            //E6 (1.5, 3.7)
            //E9 (1.5, 6)
            //A9 (-1.8, 6)
            //C9 (0, 6)
            //C10 (0, 7)
        
        
        
        // new Pose2d(new Translation2d(-0.3, 0)/* end (0, 0)*/ , new Rotation2d(3 * (Math.PI / 2))), 
        //     List.of( //0.762 per box
        //       new Translation2d(0,2.8),
        //       new Translation2d(0.5, 4),
        //       new Translation2d(1.6, 2.8),
        //       new Translation2d(0.8, 2),
        //       new Translation2d(0, 2),
        //       new Translation2d(-0.3, 5.8),
        //       new Translation2d(-1.5, 5.8),
        //       new Translation2d(-2.1, 4.5),
        //       new Translation2d(-1.1, 3.8),
        //       new Translation2d(1, 6.25),
        //       new Translation2d(-0.1, 6.75)
        //     //^^^^Circle Cource
        //     ),



        // new Pose2d(new Translation2d(-1.75, 0), new Rotation2d(3 * (Math.PI / 2))), 
        //     List.of( //0.762 per box
        //     new Translation2d(-0.75, 1),// middle of the first turn
        //     new Translation2d(-1.5, 2), // end of first turn
        //     new Translation2d(-1.5, 4.7), // first stight away
        //     new Translation2d(-0.75, 5.7), // middle of second turn
        //     new Translation2d(0, 6.5), // end of second turn
        //     new Translation2d(-0.75, 7.25), // middle of end turn // circle part 
        //     new Translation2d(-1.5, 6.5), // end of end tunr // circle part
        //     new Translation2d(-0.75, 5.7), // mid of third turn 
        //     new Translation2d(0, 4.7), // end of third turn
        //     new Translation2d(0, 2), // end of stright away 
        //     new Translation2d(-0.75, 1.25) // turn to end 
        //     ///^^^^SLOLEM COURSE
        //     ),
             driveTrain);
            
          trajectoryGeneration.generateTrajectory();
        driveTrain.setDefaultCommand(
            new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust, true));

        thumbButton = new JoystickButton(joystick, 2);
        twelveButton = new JoystickButton(joystick, 12);
        trigger = new JoystickButton(joystick, 1);
        threeButton = new JoystickButton(joystick, 3);
        five = new JoystickButton(joystick, 5);
        six = new JoystickButton(joystick, 6);
        eleven = new JoystickButton(joystick, 11);
        four = new JoystickButton(joystick, 4);
        ten = new JoystickButton(joystick, 10);
        nine = new JoystickButton(joystick, 9);
        eight = new JoystickButton(joystick, 8);
        // Configure the button bindings
        configureButtonBindings();
        SmartDashboard.putNumber("Auton Chooser", 0);

        shooterLights.set(true);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        thumbButton.toggleWhenPressed(
            new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust, false));
        /*trigger.toggleWhenPressed(new MotorCommand(flyWheel, 1));
        threeButton.toggleWhenPressed(new MotorCommand(hood, -0.2));
        five.whenPressed(new MotorCommand(turret, 0.2));
        six.whenPressed(new MotorCommand(turret, -0.2));
        eleven.toggleWhenPressed(new MotorCommand(indexer, 0.2));
        four.whenPressed(new MotorCommand(hood, 0.2));
        ten.toggleWhenPressed(new MotorCommand(intake, -0.6));*/
        //trigger.whileHeld(new FunctionalCommand(() -> shooterLights.set(true), () -> {}, (b) -> shooterLights.set(false), () -> false, shooterLights));
        //threeButton.whileHeld(new FunctionalCommand(() -> hood.turnOn(0.5), () -> {}, (b) -> hood.turnOff(), () -> false, hood));
        //four.whileHeld(new FunctionalCommand(() -> hood.turnOn(-0.5), () -> {}, (b) -> hood.turnOff(), () -> false, hood));
        //twelveButton.whileHeld(new FunctionalCommand(() -> shooterLights.set(true), () -> {}, (b) -> shooterLights.set(false), () -> false, shooterLights));
        //nine.toggleWhenPressed(new ResetPoistion(driveTrain));
        motorSubsystemButton(operatorController.buttons.dPad.up , hood, 0.8, false);
        motorSubsystemButton(operatorController.buttons.dPad.down, hood, -0.8, false);
        motorSubsystemButton(operatorController.buttons.dPad.right, turret, -0.25, false);
        motorSubsystemButton(operatorController.buttons.dPad.left, turret, 0.25, false);
        motorSubsystemButton(operatorController.buttons.Y, flyWheel, 1, true);
        motorSubsystemButton(operatorController.buttons.RB, intake, 0.5, true);
        // motorSubsystemButton(operatorController.buttons.B, indexer, 0.8, true);
        // motorSubsystemButton(operatorController.buttons.X, indexer, -0.8, true);
        motorSubsystemButton(operatorController.buttons.LB, feeder, 0.5, false);

        new Trigger(() -> operatorController.triggers.right.get() > 0.5).whileActiveOnce(new VisionShoot(turret, flyWheel, indexer, feeder, hood, pi));
        operatorController.buttons.A.toggleWhenPressed(new FunctionalCommand(() -> flyWheel.reset(), () -> flyWheel.setMotorMaxOmega(), (b) -> flyWheel.setOff(), () -> false, flyWheel)); 
        operatorController.buttons.X.toggleWhenPressed(new FunctionalCommand(() -> hood.setPosition(1000), () -> {}, (b) -> hood.turnOff(), () -> false, hood));
        
        nine.whenPressed(new KVCommand(driveTrain));
        eleven.whenPressed(new ResetPoistion(driveTrain));
        operatorController.buttons.B.toggleWhenPressed(new FunctionalCommand(() -> hood.calibrate(), () -> {}, (b) -> hood.turnOff(), () -> false, hood)); 
        indexer.setDefaultCommand(new FunctionalCommand(() -> {}, () -> indexer.turnOn(operatorController.sticks.left.getY()), (b) -> {}, () -> false, indexer));
        //left joystick index right joystick elevator

        //shooterLights.setDefaultCommand(new FunctionalCommand(() -> shooterLights.set(true), () -> {}, (b) -> shooterLights.set(false), () -> false, shooterLights));
    }
    public void motorSubsystemButton(JoystickButton jB, MotorSubsystem subsystem, double velocity, boolean toggle) {
      if(toggle){
        jB.toggleWhenPressed(new FunctionalCommand(() -> subsystem.turnOn(velocity), () -> {}, (b) -> subsystem.turnOff(), () -> false, subsystem));
      }
      else{
        jB.whileHeld(new FunctionalCommand(() -> subsystem.turnOn(velocity), () -> {}, (b) -> subsystem.turnOff(), () -> false, subsystem));
      }   
    }
    public void motorSubsystemButton(Button jB, MotorSubsystem subsystem, double velocity, boolean toggle) {
        if(toggle){
          jB.toggleWhenPressed(new FunctionalCommand(() -> subsystem.turnOn(velocity), () -> {}, (b) -> subsystem.turnOff(), () -> false, subsystem));
        }
        else{
          jB.whileHeld(new FunctionalCommand(() -> subsystem.turnOn(velocity), () -> {}, (b) -> subsystem.turnOff(), () -> false, subsystem));
        }   
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
    public void generateTrajectory(boolean generate){
        if(generate){
          trajectoryGeneration.generateTrajectory();
        }
        else{

        }
    }
    // public void trajectory(TrajectoryGeneration trajectoryGeneration, DriveTrain driveTrain, Pose2d ){
    //   trajectoryGeneration = new TrajectoryGeneration(driveTrain.getPose(),
    //         new Pose2d(new Translation2d(0, 2), new Rotation2d(Math.PI/2)), 
    //         List.of(new Translation2d(0,1)), driveTrain);
    // }
    // * @return the command to run in autonomous
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        //return new AutonomusCommand(driveTrain, gate, pastaPuller, hangerArm);
        //PLAN: subsystem to generate trajectory, brings in the tragectory into here and those paramiters, kiniatics is handledd by DriveTrain, 
        //Ramsete Command will be made Here and this method will just return that. 
        return //new FunctionalCommand(() -> flyWheel.turnOn(-1), () -> {}, (b) -> {}, () -> true, flyWheel).alongWith(
               //new FunctionalCommand(() -> intake.turnOn(-1), () -> {}, (b) -> {}, () -> true, intake)).andThen( 
               (new RamseteCommand(
                trajectoryGeneration.getTrajectory(),
                driveTrain::getPose,
                new RamseteController(),
                new SimpleMotorFeedforward(Constants.VoltageConstants.kS, Constants.VoltageConstants.kV, Constants.VoltageConstants.kA),
                driveTrain.kinematics,
                driveTrain::getWheelSpeeds,
                new PIDController(Constants.VoltageConstants.kP, 0, 0), 
                new PIDController(Constants.VoltageConstants.kP, 0, 0),
                driveTrain.tankDriveVolts,
                driveTrain));
    }
    
}