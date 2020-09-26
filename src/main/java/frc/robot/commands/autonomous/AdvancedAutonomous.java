package frc.robot.commands.autonomous;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SubsystemContainer;
import frc.robot.commands.TimedDrive;

public class AdvancedAutonomous extends SequentialCommandGroup {

    public static ObjectMapper mapper = new ObjectMapper();

    public AdvancedAutonomous() {

    }

    public AdvancedAutonomous(ArrayList<CommandBase> commands) {
        addCommands(commands.toArray(new CommandBase[commands.size()]));
    }

    public static AdvancedAutonomous load(SubsystemContainer subs) {
        Map<String, String> env = System.getenv();

        JsonNode data;
        try {
            data = mapper.readTree(new File(env.get("HOME") + "/autonomous.json"));
        } catch (IOException e) {
            e.printStackTrace();
            return new AdvancedAutonomous();
        }

        return load(subs, data);
    }

    public static AdvancedAutonomous load(SubsystemContainer subs, JsonNode data) {
        ArrayNode jsonCommands = (ArrayNode) data.get("commands");
        ArrayList<CommandBase> commands = new ArrayList<CommandBase>();

        for (JsonNode jsonCommand : jsonCommands) {
            try {
                commands.add(parse(subs, jsonCommand));
            } catch (JsonProcessingException e) {
                System.out.println("Could not parse command: ");
                e.printStackTrace();
            }
        }

        return new AdvancedAutonomous(commands);
    }

    private static CommandBase parse(SubsystemContainer subs, JsonNode jsonCommand) throws JsonProcessingException {
        JsonNode value = jsonCommand.get("value");
        switch (jsonCommand.get("type").asText()) {
            //case "DriveStraightUntilStop": return DriveStraightUntilStop.fromJson(subs, value);
            case "TimedDrive": return TimedDrive.fromJson(subs, value);
        }

        throw new RuntimeException("Unknown command: " + jsonCommand.get("type").asText());
    }
}