package be.bielen.twitterwall.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class TwitterClient {

    @Autowired
    private Twitter twitter;

    public SearchResults searchTweets(String searchString) {
        SearchResults results = null;

        if (StringUtils.isNotBlank(searchString)) {
            results = twitter.searchOperations().search(searchString, 10);
        }

        return results;
    }

}
