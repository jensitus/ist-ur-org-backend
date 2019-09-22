package org.ist.ur.org.common;

public enum RestPoints {

  BACKENDURL("http://localhost:4200");

  private String value;

  RestPoints(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
