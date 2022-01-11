package com.example.jwttest.Service;

import com.example.jwttest.Dao.UserDao;
import com.example.jwttest.Vo.PatientUserVo;
import com.example.jwttest.Vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public Boolean checkDuplicate(String userId){
        return userDao.duplicateCheck(userId);
    }

    public ResultVo signUp(PatientUserVo user){
        if(checkDuplicate(user.getPatientId())){
            return ResultVo.builder().result("fail").msg("Fail Duplicated").build();
        }
        user = userDao.insertUserData(user);
        if(user==null)
            return ResultVo.builder().result("fail").msg("Fail Insert").build();
        else
            return ResultVo.builder().result("success").msg("").data(user).build();
    }
}
