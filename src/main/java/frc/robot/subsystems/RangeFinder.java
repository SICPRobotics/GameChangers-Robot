/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public final class RangeFinder extends SubsystemBase {
    private AnalogInput ultrasonicInput = new AnalogInput(Constants.UltrasonicSensor.ULTRASONIC_SENSOR_ID);
    
    public RangeFinder() {
        
    }

    public final double getCmDistance() {   
        return ultrasonicInput.getVoltage() / Constants.UltrasonicSensor.VOLT_TO_CM;
    }
}
