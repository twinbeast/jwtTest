package com.example.jwttest.Entity;

import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
//@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="jwt_auth")
public class JwtAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authNo", unique=true, nullable=false)
    private Long authNo;

    @NotNull
    @Column(name = "patientNo", nullable=false)
    private Long patientNo;

    @NotNull
    @Column(name = "refreshToken", nullable=false)
    private String token;

    @NotNull
    @Column(name = "expireDateTime", nullable=false)
    private String expireDateTime;

    @NotNull
    @Column(name = "status", columnDefinition = "int(1) default 0", nullable=false)
    private int status;

    @NotNull
    @Column(name = "regDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable=false)
    private LocalDate regDate;

}
