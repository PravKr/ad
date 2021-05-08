package com.ad.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @Autowired
    HttpServletRequest requestHeader;

    @RequestMapping("/user")
    public String welcomeUser() {
        System.out.println("========================" + decode(requestHeader.getHeader("Authorization"))[0]);
        return "User has successfully logged in!!!";

    }

    @RequestMapping("/admin")
    public String welcomeAdmin() {
        return "Admin has successfully logged in!!!";
    }

    private static String[] decode(final String encoded) {
        final byte[] decodedBytes
                = Base64.decodeBase64(encoded.getBytes());
        final String pair = new String(decodedBytes);
        final String[] userDetails = pair.split(":", 2);
        System.out.println("========================" +pair);
        return userDetails;
    }

}