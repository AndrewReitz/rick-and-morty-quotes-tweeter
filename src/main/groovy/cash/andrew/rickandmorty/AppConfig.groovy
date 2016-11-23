package cash.andrew.rickandmorty

class AppConfig {
  Properties configProperties

  AppConfig() {
    def configResourcePath = "/rick-and-morty-quotes-twitter-config.properties"
    def configResourceStream = getClass().getResourceAsStream(configResourcePath)
    if (!configResourceStream) {
      throw new RuntimeException("AppConfig properties resource file not found at $configResourceStream. Run 'writeConfig' task to generate it.")
    }
    configProperties = new Properties()
    configProperties.load(configResourceStream)
  }

  boolean getDebug() {
    return configProperties['debug'] == 'true'
  }

  String getOAuthConsumerSecret() {
    return configProperties['oAuthConsumerSecret']
  }

  String getOAuthAccessToken() {
    return configProperties['oAuthAccessToken']
  }

  String getOAuthAccessTokenSecret() {
    return configProperties['oAuthAccessTokenSecret']
  }

  String getOAuthConsumerKey() {
    return configProperties['oAuthConsumerKey']
  }
}
