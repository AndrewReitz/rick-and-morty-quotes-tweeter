package cash.andrew.rickandmorty

import spock.lang.Specification
import twitter4j.Twitter

class TweeterSpec extends Specification {

  void "tweetSeparator should default to ellipse"() {
    given:
    def twitter = Mock(Twitter)

    when:
    def tweeter = new Tweeter(twitter)

    then:
    tweeter.tweetSeparator == Tweeter.ELLIPSE
  }

  void "tweetSeparator should be set when not null or empty"() {
    given:
    def twitter = Mock(Twitter)
    String expected = 'separator'

    when:
    def tweeter = new Tweeter(twitter, expected)

    then:
    tweeter.tweetSeparator == expected
  }

  void "should call update status once for a tweet that is less than 140 characters"() {
    given:
    def twitter = Mock(Twitter)
    def tweeter = new Tweeter(twitter)
    def expected = 'This is a tweet'

    when:
    tweeter.tweet(expected)

    then:
    1 * twitter.updateStatus(expected)
  }

  void "should call update status once for a tweet that is exactly 140 characters"() {
    given:
    def twitter = Mock(Twitter)
    def tweeter = new Tweeter(twitter)
    def tweet = (0..138).inject('a', { a, b -> 'a' + a })

    when:
    tweeter.tweet(tweet)

    then:
    1 * twitter.updateStatus(tweet)
  }

  void "should tweet twice with one separator when the message is 279 characters and there are no spaces in the message"() {
    given:
    def twitter = Mock(Twitter)
    def tweeter = new Tweeter(twitter)
    def expected1 = (0..137).inject('a', { a, b -> 'a' + a })
    def expected2 = (0..138).inject('b', { a, b -> 'b' + a })
    def tweet = expected1 + expected2

    when:
    tweeter.tweet(tweet)

    then:
    1 * twitter.updateStatus(expected1 + Tweeter.ELLIPSE)
    1 * twitter.updateStatus(expected2)
  }

  void "should tweet twice with one separator that is 3 characters long"() {
    given:
    def twitter = Mock(Twitter)
    def tweeter = new Tweeter(twitter, '$$$')
    def expected1 = '''threw a wish in the well Don't ask me I'll never tell I looked at you as it fell And now you're in my way I trade my soul for a wish '''
    def expected2 = '''Pennies and dimes for a kiss I wasn't looking for this But now you're in my way Your stare was holding Ripped jeans Skin was showing'''
    def tweet = expected1 + expected2

    when:
    tweeter.tweet(tweet)

    then:
    1 * twitter.updateStatus(expected1 + '$$$')
    1 * twitter.updateStatus(expected2)
  }

  void "should tweet three times with two separators that is 5 characters long"() {
    given:
    def twitter = Mock(Twitter)
    def tweeter = new Tweeter(twitter, '$$$$$')
    def expected1 = '''threw a wish in the well Don't ask me I'll never tell I looked at you as it fell And now you're in my way I trade my soul for a wish '''
    def expected2 = '''Pennies and dimes for a kiss I wasn't looking for this But now you're in my way Your stare was holding Ripped jeans Skin was showing '''
    def expected3 = '''Hot was blowing Where you think you're going baby?'''
    def tweet = expected1 + expected2 + expected3

    when:
    tweeter.tweet(tweet)

    then:
    1 * twitter.updateStatus(expected1 + '$$$$$')
    1 * twitter.updateStatus(expected2 + '$$$$$')
    1 * twitter.updateStatus(expected3)
  }
}
