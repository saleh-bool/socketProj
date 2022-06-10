package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
@Setter
public class StudentLicenceCodeValue implements StudentValue {
    List<LicenseValuePair> licenceCodeValuePairs;

    @Builder
    @Getter
    @Setter
    public static class LicenseValuePair {
        long licenceCode;
        double value;
    }
}
