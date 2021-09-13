package com.pct.parser.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReverseGeoDecoding {

	private static List<String> customerNames;

	// private static final String GOOGLE_MAP_URL =
	// "https://maps.googleapis.com/maps/api/geocode/json?latlng=";

	private static final String HERE_API_URL = "https://revgeocode.search.hereapi.com/v1/revgeocode?at=";

	private static String key;

	private static JsonParser parser = new JsonParser();

	public static void init() {
		Messages.LogListenerInfoMessagesToFile("Initializing Reverse geo decoding.");
		Properties property = PropertyReader.populateProperties();
		String custIds = property.getProperty("geo.decoding.subs.customer");
		if (StringUtils.isNotEmpty(custIds)) {
			String[] ids = custIds.split(",");
			if (ids != null && ids.length > 0) {
				customerNames = new ArrayList<>(ids.length);
				for (String subscribedCustomers : ids) {
					customerNames.add(subscribedCustomers);
				}
				key = property.getProperty("google.map.key");
			}
			RequestConfig.Builder requestBuilder = RequestConfig.custom();
			requestBuilder.setConnectTimeout(timeout);
			requestBuilder.setConnectionRequestTimeout(timeout);

			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setDefaultRequestConfig(requestBuilder.build());
			client = builder.build();
			Messages.LogListenerInfoMessagesToFile(
					String.format("Reverse geo decoding httpclient initialized: Found customer info:%s", custIds));
		} else {
			Messages.LogListenerInfoMessagesToFile(
					String.format("Reverse geo decoding httpclient did not initialized: Customer info not found"));
		}

	}

	static int timeout = Integer.parseInt(PropertyReader.populateProperties().getProperty("geo.decoding.http.timeout"));

	private static HttpClient client;

	public static String populateAddress(Double lat, Double lon, String customer) {
		String address = null;
		if (lat == null || lon == null || customer == null || customerNames == null || customerNames.isEmpty()
				|| StringUtils.isEmpty(key) || StringUtils.isEmpty(customer)) {
			return address;
		}
		if (!customerNames.contains(customer)) {
			return address;
		}
		// String url = GOOGLE_MAP_URL + Double.toString(lat) + "," +
		// Double.toString(lon) + "&key=" + key;
		String url = HERE_API_URL + lat + "," + lon + "&lang=en-US&apiKey=" + key;
		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");
		HttpResponse response;
		try {
			response = client.execute(request);
			HttpEntity httpEntity = response.getEntity();
			String output = EntityUtils.toString(httpEntity);
			JsonObject object = parser.parse(output).getAsJsonObject();
			// object.getAsJsonArray("results");
			// JsonElement formatted_address =
			// object.getAsJsonArray("results").get(1).getAsJsonObject().get("formatted_address");
			JsonElement formatted_address = object.get("items").getAsJsonArray().get(0).getAsJsonObject().get("address")
					.getAsJsonObject().get("label");
			address = formatted_address.getAsString();
		} catch (Exception e) {
			Messages.LogListenerErrorsToFile("populateAddress error: " + e + Messages.GetExceptionInfo(e));
		}

		return address;
	}

}
