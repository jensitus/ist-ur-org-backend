package org.ist.ur.org.common.message;

public class Message {

  private String text;

  private Boolean bool;

  public Message() {
  }

  public Message(String text, Boolean bool) {
    this.text = text;
    this.bool = bool;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getBool() {
    return bool;
  }

  public void setBool(Boolean bool) {
    this.bool = bool;
  }

  @Override
  public String toString() {
    return "Message{" +
            "text='" + text + '\'' +
            ", trueOrFalse=" + bool +
            '}';
  }
}
