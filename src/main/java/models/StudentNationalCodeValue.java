package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
@Getter
@Setter
public class StudentNationalCodeValue implements StudentValue {
    Map<Long, Double> nationalCodeValuePairs;
}
