package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.StudentGenerationException;
import lombok.SneakyThrows;
import models.Student;
import socket.Transmitter;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static commands.CommandManger.COMMAND_MAP;
import static server.Server.SERVER_PORT;

public class Client {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Scanner SCANNER = new Scanner(System.in);

    private final Set<Student> students;
    private Transmitter transmitter;

    private Client() {
        students = new HashSet<>();
        getStudentFromUser();
        connectToServer();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.requestingLoop();
        client.transmitter.disconnect();
    }

    private void getStudentFromUser() {
        String input;

        System.out.println("do you wanna add a student? (y/else)");
        input = SCANNER.nextLine();
        if (!input.equals("y")) return;

        System.out.println("type like this \"name,family,nationCode,licenceCode\"");
        input = SCANNER.nextLine();
        Student student = null;
        try {
            student = Student.from(input);
        } catch (StudentGenerationException e) {
            e.printStackTrace();
            getStudentFromUser();
        }

        System.out.println("type the scores of lessons like this \"l1 s1,l2 s2, ...\"");
        input = SCANNER.nextLine();
        try {
            student.setScoresFromInput(input);
        } catch (StudentGenerationException e) {
            e.printStackTrace();
            getStudentFromUser();
        }

        students.add(student);
        getStudentFromUser();
    }

    @SneakyThrows
    private void connectToServer() {
        transmitter = new Transmitter();
        transmitter.connect(SERVER_PORT);
        transmitter.send(mapper.writeValueAsString(students));
        System.err.println(transmitter.receive());
    }

    private void requestingLoop() {
        String input;
        System.out.println("type your command (average, sort, max, min, else)");
        input = SCANNER.nextLine();
        if (!COMMAND_MAP.containsKey(input)) {
            transmitter.send(input);
            return;
        }
        transmitter.send(input);
        System.err.println(transmitter.receive());
        requestingLoop();
    }
}
