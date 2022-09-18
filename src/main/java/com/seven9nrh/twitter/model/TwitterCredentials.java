package com.seven9nrh.twitter.model;

public class TwitterCredentials {

  private String bearerToken;

  public TwitterCredentials(String bearerToken) {
    this.bearerToken = bearerToken;
  }

  public String getBearerToken() {
    return bearerToken;
  }

  public void setBearerToken(String bearerToken) {
    this.bearerToken = bearerToken;
  }

  @Override
  public String toString() {
    return "TwitterCredentials [bearerToken=" + bearerToken + "]";
  }
}
