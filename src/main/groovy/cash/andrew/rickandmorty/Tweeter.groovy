package cash.andrew.rickandmorty

import com.google.common.base.Strings
import twitter4j.Twitter

import static com.google.common.base.Preconditions.checkNotNull

class Tweeter {
  private static final int TWEET_LENGTH = 140
  private static final char ELLIPSE = '…'

  private final Twitter twitter
  private final String tweetSeparator

  Tweeter(Twitter twitter) {
    this(twitter, null)
  }

  Tweeter(Twitter twitter, String tweetSeparator) {
    this.twitter = checkNotNull(twitter, 'twitter == null')
    this.tweetSeparator = Strings.isNullOrEmpty(tweetSeparator) ? ELLIPSE : tweetSeparator
  }

  /**
   * Tweet a message. If this message is over 140 characters it will be split up 140 characters
   * using the separator and tweeting them in reverse order so it is easy to read on twitter.
   *
   * @param message the message to tweet.
   */
  void tweet(String message) {
    if (message.size() <= 140) {
      twitter.updateStatus(message)
      return
    }

    def tweet = message.substring(0, TWEET_LENGTH)
    def startIndex = tweet.size() - tweet.reverse().indexOf(' ')

    // unlikely situation where there is no space, 141 because if there is no
    // space indexOf will return -1 and 140 - -1 = 140
    if (startIndex == 141) {
      startIndex = TWEET_LENGTH - tweetSeparator.size()
    } else if (startIndex < tweetSeparator.size() + TWEET_LENGTH) { // no room for separator try again
      tweet = tweet.substring(0, tweet.size() - tweetSeparator.size())
      startIndex = tweet.size() - tweet.reverse().indexOf(' ')
    }

    this.tweet(message.substring(startIndex, message.length()))
    this.tweet(message.substring(0, startIndex) + tweetSeparator)
  }

//  void tweet(String tweet) {
//    int tweetLengthNoSeparator = TWEET_LENGTH - tweetSeparator.size()
//    int numberOfTweets = Math.ceil(tweet.size() / TWEET_LENGTH)
//
//    def tweetStrings = []
//    int endIndex = numberOfTweets - 1
//    (0..endIndex).each {
//
//      int stringStart = it * tweetLengthNoSeparator
//      int stringEnd = (it + 1) * tweetLengthNoSeparator
//      stringEnd = stringEnd > tweet.size() ? tweet.size() : stringEnd
//
//      String addSeparator = (endIndex == it ? '' : tweetSeparator)
//
//      tweetStrings << "${tweet.substring(stringStart, stringEnd)}$addSeparator"
//    }
//
//    tweetStrings.reverse().each { String it ->
//      twitter.updateStatus(it)
//    }
//  }
}
