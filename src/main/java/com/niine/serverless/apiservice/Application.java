package com.niine.serverless.apiservice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.niine.serverless.apiservice.controller.PingController;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "com.niine.serverless.controller")
@Import({ com.niine.serverless.apiservice.controller.PingController.class })
@RestController
@RequestMapping("/apiservice")
public class Application {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/externalApi")
	public String getExternalApiResponse() {
      String url = "https://api.fieldassist.in/api/V3/Visit/detailedVisit?lastVisitId=1319641553&includeUnproductive=false"; // Replace with your external API URL

      // Username and password for basic authentication
      String username = "Niine_10543";
      String password = "BJanftQsvsWP6V45Apg_";

      // Create the Basic Auth header by encoding username and password
      String auth = username + ":" + password;
      String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
      String authHeader = "Basic " + encodedAuth;

      // Set headers
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", authHeader);

      // Create HttpEntity with headers
      HttpEntity<String> entity = new HttpEntity<>(headers);

      // Send request with Basic Authentication headers
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

      return response.getBody();
  }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}