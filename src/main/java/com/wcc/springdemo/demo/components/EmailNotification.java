package com.wcc.springdemo.demo.components;

import org.springframework.stereotype.Component;

@Component
public class EmailNotification {

  public void sendEmail(String to, String subject, String body) {
    System.out.println("Sending email to " + to + " with subject " + subject + " and body " + body);
  }
}
