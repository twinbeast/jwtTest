package com.example.jwttest.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUserVo {
    private int patientNo;
    private String patientId;
    private String paitentPw;
    private String firstName;
    private String lastName;
    private String country;
    private String dateOfBirth;
    private String gender;
    private int status;
    private String regDate;
}
