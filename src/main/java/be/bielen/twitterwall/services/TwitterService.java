package be.bielen.twitterwall.services;

import org.springframework.social.twitter.api.Tweet;

import java.util.List;

public interface TwitterService {

    List<Tweet> searchForTweets(String searchString);
}
