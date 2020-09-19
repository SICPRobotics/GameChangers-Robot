package frc.robot.commands;

import com.fasterxml.jackson.databind.JsonNode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SubsystemContainer;

public abstract class Deserializable {
    static abstract CommandBase fromJson(SubsystemContainer subs, JsonNode json);
}