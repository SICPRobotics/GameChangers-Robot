package frc.robot.commands.autonomous;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SubsystemContainer;
import frc.robot.commands.DriveStraightUntilStop;
import frc.robot.commands.TimedDrive;

public class AdvancedAutonomous extends SequentialCommandGroup {

    public static ObjectMapper mapper = new ObjectMapper();

    public static AdvancedAutonomous load() {
        Map<String, String> env = System.getenv();

        JsonNode data;
        try {
            data = mapper.readTree(new File(env.get("HOME") + "/autonomous.json"));
        } catch (IOException e) {
            e.printStackTrace();
            return new AdvancedAutonomous();
        }

    }

    private static CommandBase parse(SubsystemContainer subs, JsonNode jsonCommand) throws JsonProcessingException {
        JsonNode value = jsonCommand.get("value");
        switch (jsonCommand.get("type").asText()) {
            //case "DriveStraightUntilStop": return DriveStraightUntilStop.fromJson(subs, value);
            case "TimedDrive": return TimedDrive.fromJson(subs, value);
        }
    }
}