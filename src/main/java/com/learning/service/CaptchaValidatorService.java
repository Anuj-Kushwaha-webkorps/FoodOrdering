package com.learning.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Map;

@Service
public class CaptchaValidatorService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean isCaptchaValid(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("secret", recaptchaSecret);
        requestData.add("response", captchaResponse);

        ResponseEntity<Map> response = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, requestData, Map.class);

        Map<String, Object> body = response.getBody();
        return body != null && (Boolean) body.get("success");
    }
}

