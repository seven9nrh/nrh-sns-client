package com.seven9nrh.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;

@Configuration
@ComponentScan
public class TwitterApiClientConfiguration {

  @Value("${com.seven9nrh.twitter.bearerToken}")
  private String bearerToken;

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public TwitterApi twitterApi() {
    TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(
      bearerToken
    );
    // Instantiate library client
    TwitterApi apiInstance = new TwitterApi(credentials);
    // Instantiate auth credentials (App-only example)
    return apiInstance;
  }
}
