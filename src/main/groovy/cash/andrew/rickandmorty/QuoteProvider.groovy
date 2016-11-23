package cash.andrew.rickandmorty

import groovy.json.JsonSlurper
import org.apache.commons.lang3.RandomUtils

import static com.google.common.base.Preconditions.checkNotNull

class QuoteProvider {

  private static final JsonSlurper JSON_SLURPER = new JsonSlurper()

  private final List<String> quotes

  /**
   * Name of json file containing quotes. This file
   * must be a list of strings.
   */
  QuoteProvider(String quoteFileName) {
    checkNotNull(quoteFileName, 'quoteFileName == null')

    def stream = getClass().getResourceAsStream(quoteFileName)

    quotes = JSON_SLURPER.parse(stream) as List<String>
  }

  String provideQuote() {
    def value = RandomUtils.nextInt(0, quotes.size())
    return quotes[value]
  }
}
