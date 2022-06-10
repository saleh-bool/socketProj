package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.CommandManger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import models.Department;
import models.Student;
import socket.Transmitter;

import java.util.HashSet;
import java.util.Set;

import static commands.CommandManger.COMMAND_MAP;

@RequiredArgsConstructor
public class ServerView {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Transmitter transmitter;
    private Department department;

    public void contact() {
        generateDepartment();
        responseLoop();
        transmitter.disconnect();
    }

    @SneakyThrows
    private void generateDepartment() {
        String studentsJson = transmitter.receive();
        JsonNode studentsJsonNode = MAPPER.readTree(studentsJson);
        Set<Student> students = new HashSet<>();
        studentsJsonNode.forEach(jsonNode -> {
            try {
                Student student = MAPPER.treeToValue(jsonNode, Student.class);
                students.add(student);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        department = new Department();
        department.setStudents(students);
        transmitter.send("student number: " + department.getStudents().size());
    }

    private void responseLoop() {
        String commandText = transmitter.receive();
        if (!COMMAND_MAP.containsKey(commandText)) return;
        transmitter.send(CommandManger.execute(commandText, department));
        responseLoop();
    }
}
