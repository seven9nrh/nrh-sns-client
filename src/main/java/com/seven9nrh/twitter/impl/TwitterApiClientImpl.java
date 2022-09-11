package com.seven9nrh.twitter.impl;

import com.seven9nrh.twitter.TwitterApiClient;
import com.seven9nrh.twitter.model.TweetData;
import com.seven9nrh.twitter.model.UserData;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TweetsApi.APItweetsRecentSearchRequest;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2TweetsSearchRecentResponse;
import com.twitter.clientlib.model.Tweet;
import com.twitter.clientlib.model.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class TwitterApiClientImpl implements TwitterApiClient {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private TwitterApi twitterApi;

  public TwitterApiClientImpl(TwitterApi twitterApi) {
    this.twitterApi = twitterApi;
  }

  @Override
  public List<TweetData> tweetsSerchRecent(String query) {
    return tweetsSerchRecentFlux(query).collectList().block();
  }

  @Override
  public Flux<TweetData> tweetsSerchRecentFlux(String query) {
    return Flux.<TweetData>create(
      fluxSink -> {
        try {
          int totalCount = 0;
          Get2TweetsSearchRecentResponse result = null;
          do {
            APItweetsRecentSearchRequest tweetsRecentSearch = twitterApi
              .tweets()
              .tweetsRecentSearch(query);
            tweetsRecentSearch
              .expansions(expansions)
              .tweetFields(tweetFields)
              .userFields(userFields)
              .maxResults(maxResults);
            if (hasNext(result)) {
              tweetsRecentSearch.nextToken(getNextToken(result));
            }
            result = tweetsRecentSearch.execute();
            logger.info("result {}", result);
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
              tl.forEach(fluxSink::next);
              totalCount += tl.size();
            }
          } while (hasNext(result) && totalCount <= maxTotalResults);
          fluxSink.complete();
        } catch (ApiException e) {
          logger.error(
            "Exception when calling TweetsApi#tweetsRecentSearch",
            e
          );
          fluxSink.error(e);
        }
      }
    );
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

  private boolean hasNext(Get2TweetsSearchRecentResponse result) {
    return getNextToken(result) != null && !getNextToken(result).isBlank();
  }

  private String getNextToken(Get2TweetsSearchRecentResponse result) {
    if (result == null || result.getMeta() == null) {
      return null;
    }
    return result.getMeta().getNextToken();
  }
}
