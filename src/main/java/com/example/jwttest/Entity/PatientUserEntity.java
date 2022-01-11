package com.example.jwttest.Entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
//@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="jwt_member")
public class PatientUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientNo", unique=true, nullable=false)
    private Long patientNo;

    @NotNull
    @Column(name = "patientId", unique=true, nullable=false)
    private String patientId;

    @NotNull
    @Column(name = "patientPw", nullable=false)
    private String patientPw;

    @NotNull
    @Column(name = "lastName", nullable=false)
    private String lastName;

    @NotNull
    @Column(name = "firstName", nullable=false)
    private String firstName;

    @NotNull
    @Column(name = "dob", nullable=false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "country", nullable=false)
    private String country;

    @NotNull
    @Column(name = "gender", nullable=false)
    private String sex;

    @NotNull
    @Column(name = "status", columnDefinition = "int(1) default 0", nullable=false)
    private int status;

    @NotNull
    @Column(name = "regDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable=false)
    private LocalDate regDate;
}
