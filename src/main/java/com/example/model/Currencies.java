package com.example.model;

import com.example.services.HttpRequests;
import com.example.services.JsonParser;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Currencies {
	public static final String CURRENCIES_APP_ID = "e455055d2c3c41b18b409092eab67ce3";
	private static final String CURRENCIES_BASE_URL = "https://openexchangerates.org/";
	private static final Map<String, String> ALL_CURRENCIES = new HashMap<>();

	public static List<com.example.model.Currency> getAllCurrencies() throws IOException {
		Map<String, Object> header = new HashMap<>();
		String url;
		header.put("Authorization", "Token " + CURRENCIES_APP_ID);

		url = CURRENCIES_BASE_URL
				+ "api/currencies.json";
		HttpResponse currenciesToday = HttpRequests.runGetRequest(url, header);
		if (currenciesToday.getStatusLine().getStatusCode() != 200)
			throw new RuntimeException("ERROR 1: Can't find exchange rates: " + currenciesToday.getStatusLine());
		String responseBodyString = HttpRequests.getResponseBody(currenciesToday);

		Map<String, String> map = JsonParser.convertJsonToMap(responseBodyString, String.class);
		ALL_CURRENCIES.putAll(map);
		List<com.example.model.Currency> currencyList = new ArrayList<>();
		map.forEach((k, v) -> currencyList.add(new Currency(k,v)));

		return currencyList;
	}

	public static boolean isDollarGrows(String currencyCode) throws IOException {
		return getRateForToday(currencyCode) - getRateForYesterday(currencyCode) > 0;
	}

	private static double getRateForToday(String currencyCode) throws IOException {
		return getRateForSomeDayBefore(currencyCode,0);
	}

	private static double getRateForYesterday(String currencyCode) throws IOException {
		return getRateForSomeDayBefore(currencyCode,1);
	}

	private static double getRateForSomeDayBefore(String currencyCode, int daysBefore) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.DAY_OF_MONTH, -daysBefore);

		Map<String, Object> header = new HashMap<>();
		String url;

		url = CURRENCIES_BASE_URL
				+ "api/historical/"
				+ dateFormat.format(date.getTime())
				+ ".json"
				+ "?app_id=" + CURRENCIES_APP_ID;
		HttpResponse currenciesToday = HttpRequests.runGetRequest(url, header);
		if (currenciesToday.getStatusLine().getStatusCode() != 200)
			throw new RuntimeException("ERROR 1: Can't find exchange rates: " + currenciesToday.getStatusLine());
		String responseBodyString = HttpRequests.getResponseBody(currenciesToday);
		return JsonParser.findDoubleValue(responseBodyString, currencyCode);
	}
}
