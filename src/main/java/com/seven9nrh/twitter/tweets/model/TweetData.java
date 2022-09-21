package com.seven9nrh.twitter.tweets.model;

import java.time.LocalDateTime;

public class TweetData {

  private String id;
  private String text;
  private LocalDateTime createdAt;
  private String authorId;
  private Boolean possiblysensitive;
  private int retweetCount;
  private int replyCount;
  private int likeCount;
  private int quoteCount;

  private UserData userData;

  public UserData getUserData() {
    return userData;
  }

  public void setUserData(UserData userData) {
    this.userData = userData;
  }

  public TweetData(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public Boolean getPossiblysensitive() {
    return possiblysensitive;
  }

  public void setPossiblysensitive(Boolean possiblysensitive) {
    this.possiblysensitive = possiblysensitive;
  }

  public int getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(int likeCount) {
    this.likeCount = likeCount;
  }

  public int getReplyCount() {
    return replyCount;
  }

  public void setReplyCount(int replyCount) {
    this.replyCount = replyCount;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public int getQuoteCount() {
    return quoteCount;
  }

  public void setQuoteCount(int quoteCount) {
    this.quoteCount = quoteCount;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
    result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + likeCount;
    result =
      prime *
      result +
      ((possiblysensitive == null) ? 0 : possiblysensitive.hashCode());
    result = prime * result + quoteCount;
    result = prime * result + replyCount;
    result = prime * result + retweetCount;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TweetData other = (TweetData) obj;
    if (authorId == null) {
      if (other.authorId != null) return false;
    } else if (!authorId.equals(other.authorId)) return false;
    if (createdAt == null) {
      if (other.createdAt != null) return false;
    } else if (!createdAt.equals(other.createdAt)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (likeCount != other.likeCount) return false;
    if (possiblysensitive == null) {
      if (other.possiblysensitive != null) return false;
    } else if (!possiblysensitive.equals(other.possiblysensitive)) return false;
    if (quoteCount != other.quoteCount) return false;
    if (replyCount != other.replyCount) return false;
    if (retweetCount != other.retweetCount) return false;
    if (text == null) {
      if (other.text != null) return false;
    } else if (!text.equals(other.text)) return false;
    return true;
  }

  @Override
  public String toString() {
    return (
      "TweetData [authorId=" +
      authorId +
      ", createdAt=" +
      createdAt +
      ", id=" +
      id +
      ", likeCount=" +
      likeCount +
      ", possiblysensitive=" +
      possiblysensitive +
      ", quoteCount=" +
      quoteCount +
      ", replyCount=" +
      replyCount +
      ", retweetCount=" +
      retweetCount +
      ", text=" +
      text +
      ", userData=" +
      userData +
      "]"
    );
  }
}
