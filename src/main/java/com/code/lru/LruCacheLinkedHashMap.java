package com.code.lru;

import java.util.LinkedHashMap;
import java.util.Map;

//Not thread safe.
public class LruCacheLinkedHashMap {

    LinkedHashMap<Integer, Integer> _map;
    int _capacity;

    LruCacheLinkedHashMap(int capacity) {
	_capacity = capacity;
	_map = new LinkedHashMap<Integer, Integer>(Math.min(1, _capacity), 0.75f, true) {
	    protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > _capacity;
	    }
	};
    }

    public int get(int key) {
	return _map.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
	_map.put(key, value);
    }

}
