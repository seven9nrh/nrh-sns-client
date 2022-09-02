package com.seven9nrh.twitter;

import com.seven9nrh.twitter.model.TweetData;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TwitterApiClient {
  /** Integer | The maximum number of results */
  Integer maxResults = 10;
  /** Integer | The maximum number of total results */
  Integer maxTotalResults = 10;

  /** Set<String> | A comma separated list of fields to expand. */
  Set<String> expansions = new HashSet<>(
    Arrays.asList("referenced_tweets.id.author_id")
  );

  /** Set<String> | A comma separated list of Tweet fields to display. */
  Set<String> tweetFields = new HashSet<>(
    Arrays.asList(
      //"attachments",
      "created_at",
      "author_id",
      // "context_annotations",
      // "geo",
      //"in_reply_to_user_id",
      "possibly_sensitive",
      "public_metrics"
      // "referenced_tweets",
      //"reply_settings",
      // "source",
      //"withheld",
      //"lang",
      //"conversation_id"
    )
  );

  /** Set<String> | A comma separated list of User fields to display. */
  Set<String> userFields = new HashSet<>(
    Arrays.asList(
      "created_at",
      "description",
      // "entities",
      "id",
      // "location",
      "name",
      // "pinned_tweet_id",
      // "profile_image_url",
      // "protected",
      // "public_metrics",
      // "url",
      "username"
      // "verified",
      // "withheld"
    )
  );

  /**
   * Recent search
   * @param query
   * @return
   */
  List<TweetData> tweetsSerchRecent(String query);
}
