package br.com.rodrigoaccorsi.routes.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RoutesUtils {

	public static JsonObject parseToJson(String sJson) {
		Gson gson = new Gson();
		return gson.fromJson(sJson, JsonObject.class);
	}

	public static <T> T castStrJsonToObject(String sJson, Class<T> T) {
		Gson gson = new Gson();
		return inferred(gson.fromJson(sJson, T));
	}

	public static <T> T inferred(Object obj) {
		return (T) obj;
	}
}
