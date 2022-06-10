package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Department;
import models.StudentValue;

import java.util.HashMap;
import java.util.Map;

public class CommandManger {
    public static final Map<String, Command> COMMAND_MAP = new HashMap<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        COMMAND_MAP.put("average", new AverageCommand());
        COMMAND_MAP.put("sort", new SortCommand());
        COMMAND_MAP.put("min", new MinCommand());
        COMMAND_MAP.put("max", new MaxCommand());
    }

    @SneakyThrows
    public static String execute(String command, Department department) {
        StudentValue studentValue = COMMAND_MAP.get(command).execute(department);
        return MAPPER.writeValueAsString(studentValue);
    }
}
