package models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StudentNameValue implements StudentValue {
    private String name;
    private String family;
    private double value;

    public static StudentNameValue from(Student student) {
        if (student == null) return null;
        return StudentNameValue.builder()
                .name(student.getName())
                .family(student.getFamily())
                .value(student.average())
                .build();
    }
}
