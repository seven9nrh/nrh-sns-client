package com.seven9nrh.twitter.tweets.impl;

import com.seven9nrh.twitter.model.TwitterCredentials;
import com.seven9nrh.twitter.tweets.TweetsLookupApi;
import com.seven9nrh.twitter.tweets.model.TweetData;
import com.seven9nrh.twitter.tweets.model.UserData;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TweetsApi.APIfindTweetByIdRequest;
import com.twitter.clientlib.api.TweetsApi.APIfindTweetsByIdRequest;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2TweetsIdResponse;
import com.twitter.clientlib.model.Get2TweetsResponse;
import com.twitter.clientlib.model.Tweet;
import com.twitter.clientlib.model.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TweetsLookupApiImpl implements TweetsLookupApi {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private ApplicationContext applicationContext;

  public TweetsLookupApiImpl(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public List<TweetData> tweets(String[] ids) {
    TwitterApi twitterApi = applicationContext.getBean(TwitterApi.class);
    return tweets(ids, twitterApi);
  }

  @Override
  public TweetData tweet(String id) {
    TwitterApi twitterApi = applicationContext.getBean(TwitterApi.class);
    return tweets(id, twitterApi);
  }

  @Override
  public List<TweetData> tweets(String[] ids, TwitterCredentials credentials) {
    TwitterApi twitterApi = createTwitterApi(credentials);
    return tweets(ids, twitterApi);
  }

  private List<TweetData> tweets(String[] ids, TwitterApi twitterApi) {
    try {
      APIfindTweetsByIdRequest request = twitterApi
        .tweets()
        .findTweetsById(Arrays.asList(ids))
        .expansions(expansions)
        .tweetFields(tweetFields)
        .userFields(userFields);
      Get2TweetsResponse result = request.execute();
      if (result.getData() != null) {
        List<TweetData> tl = result
          .getData()
          .stream()
          .map(this::toTweetData)
          .collect(Collectors.toList());
        for (TweetData t : tl) {
          t.setUserData(
            toUserData(
              result
                .getIncludes()
                .getUsers()
                .stream()
                .filter(u -> u.getId().equals(t.getAuthorId()))
                .findAny()
                .orElse(new User())
            )
          );
        }
        return tl;
      }
    } catch (ApiException e) {
      logger.error("tweets", e);
    }
    return new ArrayList<>();
  }

  private TweetData tweets(String id, TwitterApi twitterApi) {
    try {
      APIfindTweetByIdRequest request = twitterApi
        .tweets()
        .findTweetById(id)
        .expansions(expansions)
        .tweetFields(tweetFields)
        .userFields(userFields);
      Get2TweetsIdResponse result = request.execute();
      if (result.getData() != null) {
        TweetData tweetData = toTweetData(result.getData());
        tweetData.setUserData(
          toUserData(
            result
              .getIncludes()
              .getUsers()
              .stream()
              .filter(u -> u.getId().equals(tweetData.getAuthorId()))
              .findAny()
              .orElse(new User())
          )
        );
        return tweetData;
      }
    } catch (ApiException e) {
      logger.error("tweets", e);
    }
    return null;
  }

  @Override
  public TweetData tweet(String id, TwitterCredentials credentials) {
    TwitterApi twitterApi = createTwitterApi(credentials);
    return tweets(id, twitterApi);
  }

  private UserData toUserData(User user) {
    var userData = new UserData(user.getId());
    userData.setName(user.getName());
    userData.setUsername(user.getUsername());
    userData.setDescription(user.getDescription());
    return userData;
  }

  private TweetData toTweetData(Tweet tweet) {
    var tweetData = new TweetData(tweet.getId());
    tweetData.setText(tweet.getText());
    tweetData.setCreatedAt(
      LocalDateTime.ofInstant(
        tweet.getCreatedAt().toInstant(),
        ZoneId.systemDefault()
      )
    );
    tweetData.setAuthorId(tweet.getAuthorId());
    tweetData.setPossiblysensitive(tweet.getPossiblySensitive());
    tweetData.setLikeCount(tweet.getPublicMetrics().getLikeCount());
    tweetData.setReplyCount(tweet.getPublicMetrics().getReplyCount());
    tweetData.setRetweetCount(tweet.getPublicMetrics().getRetweetCount());
    tweetData.setQuoteCount(tweet.getPublicMetrics().getQuoteCount());
    return tweetData;
  }

  private TwitterApi createTwitterApi(TwitterCredentials credentials) {
    var twitterCredentialsBearer = new TwitterCredentialsBearer(
      credentials.getBearerToken()
    );
    TwitterApi apiInstance = new TwitterApi(twitterCredentialsBearer);
    return apiInstance;
  }
}
