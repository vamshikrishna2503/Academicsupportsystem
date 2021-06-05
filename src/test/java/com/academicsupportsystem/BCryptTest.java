package com.academicsupportsystem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {

    public static final Logger log = LoggerFactory.getLogger(BCryptTest.class);

    @Test
    public void testHashWithSalt(){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String hashedPass1 = bCryptPasswordEncoder.encode("password");
        String hashedPass2 = bCryptPasswordEncoder.encode("password");

        log.info(hashedPass1);
        log.info(hashedPass2);

        log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches("password", hashedPass1));
        log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches("password", hashedPass2));

        //trying to match with a previously generated value for same password. Should be 'true'
        log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches("password", "$2a$10$mP.DNH3LIy/PeIM84y1nhuq76w98b8ANcxH3bzxPjiXHUdSl3XFri"));
        log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches("password", "$2a$10$mP.DNH3LIy/PeIM84y1nhuq77w98b8ANcxH3bzxPjiXHUdSl3XFri"));
        log.info("Result of matching: {}" ,bCryptPasswordEncoder.matches("paSSword", "$2a$10$mP.DNH3LIy/PeIM84y1nhuq76w98b8ANcxH3bzxPjiXHUdSl3XFri"));

    }

}