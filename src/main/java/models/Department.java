package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class Department {
    Set<Student> students;

    public StudentNationalCodeValue average() {
        Map<Long, Double> collect = students.stream().collect(Collectors.toMap(Student::getNationCode, Student::average));
        return StudentNationalCodeValue.builder()
                .nationalCodeValuePairs(collect)
                .build();
    }

    public StudentLicenceCodeValue sort() {
        return StudentLicenceCodeValue.builder()
                .licenceCodeValuePairs(
                        students.stream()
                                .sorted(Comparator.comparingDouble(Student::average))
                                .map(student -> StudentLicenceCodeValue.LicenseValuePair.builder()
                                        .licenceCode(student.getLicenceCode())
                                        .value(student.average())
                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    public StudentNameValue max() {
        return StudentNameValue.from(
                students.stream()
                        .max(Comparator.comparingDouble(Student::average)).get());
    }

    public StudentNameValue min() {
        return StudentNameValue.from(
                students.stream()
                        .min(Comparator.comparingDouble(Student::average)).get());
    }
}
