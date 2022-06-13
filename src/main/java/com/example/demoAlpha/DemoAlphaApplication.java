package com.example.demoAlpha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class DemoAlphaApplication {
    public static final String CURRENCIES_APP_ID = "e455055d2c3c41b18b409092eab67ce3";
    public static final String GIFS_APP_ID = "UYl4BiGySC3iJiFVtcvCQP0wTYl2F4Jb";
    private static final String CURRENCIES_BASE_URL = "https://openexchangerates.org/";
    private static final String GIFS_BASE_URL = "https://api.giphy.com/v1/gifs/search";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(DemoAlphaApplication.class, args);
    }
	public static double getRubRateForToday() throws IOException {
		return getRubRateForSomeDayBefore(0);
	}

	public static double getRubRateForYesterday() throws IOException {
		return getRubRateForSomeDayBefore(-1);
	}

	private static double getRubRateForSomeDayBefore(int daysBefore) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.DAY_OF_MONTH, daysBefore);
		Map<String, Object> header = new HashMap<>();
		String url;
		header.put("Authorization", "Token " + CURRENCIES_APP_ID);

		url = CURRENCIES_BASE_URL
				+ "api/historical/"
				+ dateFormat.format(date.getTime())
				+ ".json"
				+ "?prettyprint=true";
		HttpResponse currenciesToday = HttpRequests.runGetRequest(url, header);
		if (currenciesToday.getStatusLine().getStatusCode() != 200)
			throw new RuntimeException("ERROR 1: Can't find exchange rates: " + currenciesToday.getStatusLine());
		String responseBodyString = HttpRequests.getResponseBody(currenciesToday);
		return JSON_MAPPER.readTree(responseBodyString).findValue("RUB").doubleValue();
	}

	public static String getRichGif() throws IOException, URISyntaxException {
		String url = new URIBuilder(GIFS_BASE_URL)
				.addParameter("api_key", GIFS_APP_ID)
				.addParameter("q", "rich")
				.addParameter("limit", "50")
				.build().toString();
		return getGif(url);
	}

	public static String getBrokeGif() throws URISyntaxException, IOException {
		String url = new URIBuilder(GIFS_BASE_URL)
				.addParameter("api_key", GIFS_APP_ID)
				.addParameter("q", "broke")
				.addParameter("limit", "50")
				.build().toString();
		return getGif(url);
	}

	private static String getGif(String url) throws IOException {
		Random random = new Random();
		Map<String, Object> header = new HashMap<>();
		HttpResponse gifResponse = HttpRequests.runGetRequest(url, header);
		String responseBody = HttpRequests.getResponseBody(gifResponse);
		JsonNode someGifData = convertJsonToList(
				JSON_MAPPER.readTree(responseBody).findValue("data").toString(),
				JsonNode.class
		).get(random.nextInt(50));

		String gifUrl = someGifData.findValue("images")
				.findValue("original")
				.findValue("url").textValue();
		return gifUrl;
	}

	private static <T> List<T> convertJsonToList(String json, Class<T> valueClass) throws JsonProcessingException {
		CollectionType collectionType = JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, valueClass);
		return JSON_MAPPER.readValue(json, collectionType);
	}

}
