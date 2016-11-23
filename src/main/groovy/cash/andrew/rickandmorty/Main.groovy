package cash.andrew.rickandmorty

import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

def appConfig = new AppConfig()

def config = new ConfigurationBuilder().with {
  debugEnabled = appConfig.debug
  setOAuthConsumerKey appConfig.getOAuthConsumerKey()
  setOAuthConsumerSecret appConfig.getOAuthConsumerSecret()
  setOAuthAccessToken appConfig.getOAuthAccessToken()
  setOAuthAccessTokenSecret appConfig.getOAuthAccessTokenSecret()
  build()
}

def twitter = new TwitterFactory(config).instance
def tweeter = new Tweeter(twitter)

def quoteProvider = new QuoteProvider('/rick-and-morty-quotes.json')

tweeter.tweet(quoteProvider.provideQuote())
