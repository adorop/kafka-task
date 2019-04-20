package com.aliaksei.darapiyevich.runner.listener;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitter4j.Place;
import twitter4j.Status;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class IsTweetForPublishingToKafkaPredicate implements Predicate<Status> {
    private final TweetProperties tweetProperties;

    @Override
    public boolean test(Status status) {
        return countryMatches(status) && languageMatches(status);
    }

    private boolean countryMatches(Status status) {
        return ofNullable(status.getPlace())
                .map(Place::getCountryCode)
                .filter(Predicate.isEqual(tweetProperties.getCountryCode()))
                .isPresent();
    }

    private boolean languageMatches(Status status) {
        return tweetProperties.getLanguageCode().equals(status.getLang());
    }
}
