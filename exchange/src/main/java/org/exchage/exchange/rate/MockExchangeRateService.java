package org.exchage.exchange.rate;

import java.util.Map;

/**
 * This service is mocked here, otherwise I might insert an API key to some
 * Internet service that it may not work.
 */
public class MockExchangeRateService {

	private static final Map<String, Double> exchangeRates = Map.of("USD-EUR", 0.85, "EUR-USD", 1.18, "USD-GBP", 0.75,
			"GBP-USD", 1.33, "EUR-GBP", 0.88, "GBP-EUR", 1.14, "EUR-EUR", 1.0, "USD-USD", 1.0, "GBP-GBP", 1.0);

	public static double getExchangeRate(String fromCurrency, String toCurrency) {
		String key = fromCurrency + "-" + toCurrency;
		if (exchangeRates.containsKey(key)) {
			return exchangeRates.get(key);
		} else {
			throw new IllegalArgumentException("Exchange rate not available for " + fromCurrency + " to " + toCurrency);
		}
	}
}