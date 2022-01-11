package com.example.jwttest.Repository;

import com.example.jwttest.Entity.PatientUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<PatientUserEntity, Long> {
    //비워있어도 잘 작동함.
    // long 이 아니라 Long으로 작성. ex) int => Integer 같이 primitive형식 사용못함

    PatientUserEntity findByPatientId(String patientId);

    // findBy뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다
    PatientUserEntity findByPatientNo(int patientNo);

    //like검색도 가능
    List<PatientUserEntity> findByFirstNameLike(String firstName);

//    PatientUserEntity saveGet(PatientUserEntity entity);
}
