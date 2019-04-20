#Kafka Task

A simple command line application, that listens for twitter stream and sends entire tweet body to Kafka.

##Tweets filtering

[FilterQuery](http://twitter4j.org/javadoc/twitter4j/FilterQuery.html) is a class which is used for filtering out tweets that don't satisfy a particular predicate. However, conditions provided to instance of this class are evaluated using logical **OR**, whereas, our case assumes **AND**.


Thus, _FilterQuery_ is provided with [keywords only](src/main/java/com/aliaksei/darapiyevich/runner/TweetPropertiesToFilterQueryConverter.java), [further filtering](src/main/java/com/aliaksei/darapiyevich/runner/listener/IsTweetForPublishingToKafkaPredicate.java) happens on _tweet received_ event.


##Build

Doesn't require extra-parameters or environment and can be built using
```
$ mvn clean package 
```

##Run

The application takes advantage of Spring Boot's _@ConfigurationProperties_ facility, therefore, is highly configurable. 


It's provided with defaults described in the task, i.e. _keywords_, _country_, _language_, credentials and Kafka broker URL should be provided via system properties.


Example:
```
$ java -Dtwitter4j.oauth.consumerKey=**** \
  -Dtwitter4j.oauth.consumerSecret=**** \
  -Dtwitter4j.oauth.accessToken=**** \
  -Dtwitter4j.oauth.accessTokenSecret=**** \
  -Dspring.kafka.bootstrap-servers=**** \
  -jar kafka-task-1.0.jar
```