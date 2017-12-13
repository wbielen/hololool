package be.bielen.twitterwall.web.delegate;

import be.bielen.twitterwall.services.TwitterService;
import be.bielen.twitterwall.web.dto.TweetDTO;
import be.bielen.twitterwall.web.exception.TwitterWallBaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TwitterWallDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterWallDelegate.class);

    @Autowired
    private TwitterService twitterService;

    public List<TweetDTO> findTweets(String searchString) throws TwitterWallBaseException {
        List<TweetDTO> tweetDTOList = new ArrayList<>();

        try {
            if (StringUtils.isNotBlank(searchString)) {
                List<Tweet> tweets = twitterService.searchForTweets(searchString);
                if (!CollectionUtils.isEmpty(tweets)) {
                    tweetDTOList = tweets.stream().
                            filter(Objects::nonNull).
                            map(this::convertTweet).
                            collect(Collectors.toList());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new TwitterWallBaseException(e.getMessage(), e);
        }

        return tweetDTOList;
    }

    private TweetDTO convertTweet(Tweet tweet) {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setId(tweet.getId());
        tweetDTO.setText(tweet.getText());
        tweetDTO.setProfileImageUrl(tweet.getProfileImageUrl());
        tweetDTO.setFromUser(tweet.getFromUser());
        tweetDTO.setCreationDate(tweet.getCreatedAt());

        return tweetDTO;
    }
}
