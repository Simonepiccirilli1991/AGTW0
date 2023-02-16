package com.gwt.service;

import com.gwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateJwtBank {

    @Autowired
    JwtUtil jwtUtil;

    public String validateJwtBank(String token) {

        String username = jwtUtil.getUsernameFromToken(token);

        Boolean resp = (userAdmit().stream().filter(x -> x.equals(username)).findAny().isPresent()) ? true : false;

        if(resp)
            return "Bank user admitted";
        else
            return "Bank user not admitted";
    }


    private List<String> userAdmit() {

        List<String> userAdmitted = new ArrayList<>();

        userAdmitted.add("bank1");
        userAdmitted.add("bank2");
        userAdmitted.add("bank3");
        userAdmitted.add("bank4");
        userAdmitted.add("Torito");

        return userAdmitted;
    }
}