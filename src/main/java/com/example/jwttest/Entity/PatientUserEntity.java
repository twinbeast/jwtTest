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
@Builder
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
    private String dateOfBirth;

    @NotNull
    @Column(name = "country", nullable=false)
    private String country;

    @NotNull
    @Column(name = "gender", nullable=false)
    private String sex;

    @Column(name = "status", nullable = false, insertable = false, columnDefinition = "int(1) default 0")
    private int status;

    @Column(name = "regDate", nullable = false, updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regDate;
}
