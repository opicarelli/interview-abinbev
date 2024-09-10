package com.opicarelli.abinbev.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.opicarelli.abinbev.challenge.data")
@SpringBootApplication
public class CodeChallengeInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeChallengeInterviewApplication.class, args);
    }

}
