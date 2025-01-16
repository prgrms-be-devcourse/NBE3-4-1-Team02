package com.example.nbe341team02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class Nbe341Team02Application {
    public static void main(String[] args) {
        SpringApplication.run(Nbe341Team02Application.class, args);

        /*
            클래스 단위에 위에 적힌 @Slf4j 를 붙이면 사용 가능합니다.
            System.out.println 보다 로그 확인하기 용이합니다.
            시간, 로그 레벨 (trace, debug, info, warn, error) 이 같이 출력됩니다.
            {} 을 통해서, String.format() 과 유사한 형태로 출력 가능합니다.
         */
        String s = "Nbe341Team02Application";
        log.info("{} started", s);
    }
}
