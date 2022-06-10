package models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import exceptions.BadInputSizeStudentGenerationException;
import exceptions.BadNumberFormatStudentGenerationException;
import exceptions.StudentGenerationException;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "nationCode")
public class Student {
    @JsonProperty("name")
    private String name;
    @JsonProperty("family")
    private String family;
    @JsonProperty("nationCode")
    private long nationCode;
    @JsonProperty("licenceCode")
    private long licenceCode;
    @JsonProperty("scores")
    private Map<String, Double> scores;

    public static Student from(String input) throws StudentGenerationException {
        String[] studentParamTexts = input.split(",");
        if (studentParamTexts.length != 4) throw new BadInputSizeStudentGenerationException();
        String nameText = studentParamTexts[0];
        String familyText = studentParamTexts[1];
        String nationCodeText = studentParamTexts[2];
        String licenceCodeText = studentParamTexts[3];
        long nationCodeValue;
        long licenceCodeValue;
        try {
            nationCodeValue = Long.parseLong(nationCodeText);
            licenceCodeValue = Long.parseLong(licenceCodeText);
        } catch (NumberFormatException e) {
            throw new BadNumberFormatStudentGenerationException();
        }
        Student student = new Student();
        student.setName(nameText);
        student.setFamily(familyText);
        student.setNationCode(nationCodeValue);
        student.setLicenceCode(licenceCodeValue);
        return student;
    }

    public double average() {
        return scores.values().stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getAverage();
    }

    public void setScoresFromInput(String input) throws StudentGenerationException {
        String[] scoresTexts = input.split(",");
        Map<String, Double> scores = new HashMap<>();
        for (String lessonScoreText : scoresTexts) {
            String[] lessonScorePair = lessonScoreText.split(" ");
            if (lessonScorePair.length != 2) throw new BadInputSizeStudentGenerationException();
            String lessonText = lessonScorePair[0];
            String scoreText = lessonScorePair[1];
            double scoreValue;
            try {
                scoreValue = Double.parseDouble(scoreText);
            } catch (NumberFormatException e) {
                throw new BadNumberFormatStudentGenerationException();
            }
            scores.put(lessonText, scoreValue);
        }
        if (scores.size() == 0) throw new BadInputSizeStudentGenerationException();
        this.scores = scores;
    }
}
