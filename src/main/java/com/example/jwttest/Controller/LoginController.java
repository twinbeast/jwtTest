package com.example.jwttest.Controller;

import com.example.jwttest.Service.UserService;
import com.example.jwttest.Service.jwt.JwtLoginService;
import com.example.jwttest.Security.jwt.JwtTokenProvider;
import com.example.jwttest.Vo.Jwt.JwtMemberModel;
import com.example.jwttest.Vo.PatientUserVo;
import com.example.jwttest.Vo.ResultVo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtLoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    Gson gson;

    @ResponseBody
    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)
    public String idDuplicateCheck(@RequestBody PatientUserVo reqData){
        log.info("idCheck: "+gson.toJson(reqData));
        return gson.toJson(userService.checkDuplicate(reqData.getPatientId()));
    }
    @ResponseBody
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@RequestBody PatientUserVo signUpData){
        return gson.toJson(userService.signUp(signUpData));
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String selectMemberByIdAndPassword(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestBody JwtMemberModel memberModel) {
        return gson.toJson(userService.signIn(response, memberModel));
    }

    @ResponseBody
    @RequestMapping(value = "/deleteCookie", method = RequestMethod.GET)
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response){
//        jwtTokenProvider.saveToken(response, null);
        jwtTokenProvider.deleteCookie(response);
    }

    @ResponseBody
    @RequestMapping(value = "/loginTest", method = RequestMethod.POST)
    public String loginTest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            log.info("cookieData : "+cookie.getName()+" - "+cookie.getValue());
        }
        return gson.toJson(ResultVo.builder().result("success").msg("").build());
    }
}
