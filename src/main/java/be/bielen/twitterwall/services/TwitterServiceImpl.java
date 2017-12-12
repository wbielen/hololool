package be.bielen.twitterwall.services;

import be.bielen.twitterwall.client.TwitterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Autowired
    private TwitterClient twitterClient;

    @Override
    public List<Tweet> searchForTweets(String searchString) {
        List<Tweet> tweets = new ArrayList<>();

        SearchResults searchResults = twitterClient.searchTweets(searchString);
        if (searchResults != null) {
            tweets = searchResults.getTweets();
        }

        return tweets;
    }
}
