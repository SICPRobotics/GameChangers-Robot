/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class Wheel {
        public static final double circumference = 0.600; //0.175 * Math.PI; 
    }
    public static final class GroundIntake {
        public static final int MOTOR_ID = 9;
        public static final double SPEED = 1.0;

        /**
         * The speed that the motor should go if RT is pressed.
         */
        public static final double SNAP_SPEED = 0.5;
    }
    
    public static final class Encoders {
        public static final int ONE_ENCODER_REVOLUTION = 4096;
    }

    public static final class Motors {
        public static final int FRONT_RIGHT_TALON = 0;
        public static final int FRONT_LEFT_TALON = 3;
        public static final int BACK_RIGHT_TALON = 1;
        public static final int BACK_LEFT_TALON = 2;
    }

    public static final class Hanger {
        public static final int ENCODER_ID_A = 0;
        public static final int ENCODER_ID_B = 1;
        public static final int ARM_MOTOR_ID = 4;
        public static final int RIGHT_WINCH_MOTOR_ID = 12;
        public static final int LEFT_WINCH_MOTOR_ID = 11;
        public static final int MAX = 11180;
        public static final double WINCH_SPEED_ADJUST = 1;
    }

    public static final class CheesyDrive {
        public static final double X_AXIS_DEADZONE_RANGE = 0.005;
        public static final double Y_AXIS_DEADZONE_RANGE = 0.005;
        public static final double X_AXIS_DEADZONE_Y_MIN = 0.1;
    }

    public static final class Compressor {
         public static final int COMPRESSOR_ID = 0;
    }

    public static final class ColorWheel {
        public static final int MOTOR_ID = 4;
        public static final int DOUBLE_SOLENOID_FORWARD_ID = 0;
        public static final int DOUBLE_SOLENOID_REVERSE_ID = 1;

        public static final int SHAFT_REVOLUTIONS_PER_GEARED_MOTOR_REVOLUTION = 50;
        public static final int MIN_SPINS = 3 * 8;
        public static final int MAX_SPINS = 5 * 8;
        public static final int SPINS_TARGET = MIN_SPINS + 6;
        
        public static final double SPEED = 1.0;
        public static final double MANUAL_SPEED = 0.5;
    }
    
    public static final class PastaPuller {
        public static final int MOTOR_ID = 6;
        public static final double SPEED = -1;
        public static final double SNAP_SPEED = 0.5;
    }
    public static final class Intake{
        public static final int MOTOR_ID = 4;
        public static final double SPEED = -1;
    }
    public static final class Gate {
        public static final int FORWARD_SOLENOID_ID = 2;
        public static final int REVERSE_SOLENOID_ID = 3;
    }
    
    public final class UltrasonicSensor {
        public static final int ULTRASONIC_SENSOR_ID = 0;
        public static final double VOLT_TO_CM = 0.00977;
        public static final double AUTON_STOPPING_DISTANCE = 40;
    }

    public final class DriveTrain {
        public static final int FRONT_RIGHT_MOTOR_ID = 1;
        public static final int FRONT_LEFT_MOTOR_ID = 2;
        public static final int REAR_RIGHT_MOTOR_ID = 0;
        public static final int REAR_LEFT_MOTOR_ID = 3;
        public static final double COUNTS_PER_ROTAION = 4096;
        public static final double WHEEL_CIRCUMFRANCE = 0.4787787204; // meters 24 1/4in(tentative) // 2021 robot 0.4787787204 // 2020 robot 0.61595
        public static final double CHASIS_TRACK_WIDTH = 0.6858; // meters
    }
    public final class VoltageConstants { // for 2020 robot update all constants in this class and the Drive Train class for new robot when possible
        public static final double kS = 1.2;
        public static final double kV = 2.57;
        public static final double kA = 0.525;
        public static final double TRACK_WIDTH = 0.7701078382789721;
        public static final double kP = 2.49;
        //r^2 = 0.998 kP = 2.49 <- in case needed later (will need Kp not r^2)
    }
    public final class MotorSubsystems{
        public static final int FLYWHEEL_ID = 10;
        public static final int INTAKE_ID = 6;
        public static final int TURRET_ID = 9;
        public static final int INDEXER_ID = 7;
    }
    public static final class Joystick {
        public static final int X_AXIS = 0;
        public static final int Y_AXIS = 1;
        public static final int Z_AXIS = 2;
        public static final int ADJUST_AXIS = 3;
    }

    public static final class PiClient {
        public static final String uri = "ws://frcvision.local:5800";
    }
}
