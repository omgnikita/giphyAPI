package com.example.demoAlpha.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.example.services.HttpRequests;
import org.example.services.JsonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Gifs {
	private static final String GIFS_APP_ID = "UYl4BiGySC3iJiFVtcvCQP0wTYl2F4Jb";
	private static final String GIFS_BASE_URL = "https://api.giphy.com/v1/gifs/search";

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
		Map<String, Object> header = new HashMap<>();
		HttpResponse gifResponse = HttpRequests.runGetRequest(url, header);
		String responseBody = HttpRequests.getResponseBody(gifResponse);
		return parseGifFromResponse(responseBody);
	}

	private static String parseGifFromResponse(String json) throws JsonProcessingException {
		Random random = new Random();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode someGifData = JsonParser.convertJsonToList(
				objectMapper.readTree(json).findValue("data").toString(),
				JsonNode.class
		).get(random.nextInt(50));

		String gifUrl = someGifData.findValue("images")
				.findValue("original")
				.findValue("url").textValue();
		return gifUrl;
	}
}
