package be.bielen.twitterwall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
@PropertySource({ "file:///Users/wilmbielen/Documents/wilm/projecten/var/twitterwall/twitter.properties" })
public class TwitterConfig {

    @Autowired
    private Environment env;

    @Bean
    public Twitter twitter() {
        String consumerKey = env.getProperty("consumer.key");
        String consumerSecret = env.getProperty("consumer.secret");
        return new TwitterTemplate(consumerKey, consumerSecret);
    }
}
