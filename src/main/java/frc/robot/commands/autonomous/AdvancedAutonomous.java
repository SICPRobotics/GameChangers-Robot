package frc.robot.commands.autonomous;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AdvancedAutonomous extends SequentialCommandGroup {

    private static ObjectMapper mapper = new ObjectMapper();

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

    private static CommandBase parse(JsonNode jsonCommand) {
        if (jsonCommand.at("type").asText().equals("DriveStraightUntilStop")) {

        }

        JsonNode value = jsonCommand.get("value");
        switch (jsonCommand.get("type").asText()) {
            case "DriveStraightUntilStop": return DriveStraightUntilStop.fromJson()
        }
    }
}