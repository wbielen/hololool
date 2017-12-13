package be.bielen.twitterwall.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchParameters;
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
            SearchParameters parameters = new SearchParameters(searchString);
            parameters = parameters.
                    geoCode(new GeoCode(50.85045, 4.34878, 100)).
                    count(20);
            results = twitter.searchOperations().search(parameters);
        }

        return results;
    }

}
