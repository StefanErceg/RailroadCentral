package org.unibl.etf.mdp.railroad.data;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DataSource {

	private static JedisPool pool = new JedisPool("localhost");
	
	public static boolean addToMap(String key, String field, JSONObject value) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hset(key, field, value.toString()) == 1;
		}
	}
	
	public static boolean removeFromMap(String key, String field) {
		try (Jedis jedis = pool.getResource()) {
			return jedis.hdel(key, field) == 1;
		}
	}
	
	public static JSONObject getFromMap(String key, String field) {
		JSONObject json = null;
		try (Jedis jedis = pool.getResource()) {
			String value = jedis.hget(key, field);
			if (value != null) {
			json = new JSONObject(jedis.hget(key, field));
			}
		}
		return json;
	}
	public static List<String> getValues(String key) {
		List<String> json = null;
		try (Jedis jedis = pool.getResource()) {
			json = jedis.hvals(key);
		}
		return json;
	}
	
}
