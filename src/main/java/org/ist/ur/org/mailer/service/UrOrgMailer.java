package org.ist.ur.org.mailer.service;

public interface UrOrgMailer {

  void getTheMailDetails(String to, String subject, String text, String salutation, String url);

}
