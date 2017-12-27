package com.code.lru;

import java.util.HashMap;
import java.util.Map;

public class LruCache {

    class LinkedNode {
	int _val;
	int _key;
	LinkedNode _next;
	LinkedNode _pre;

	public LinkedNode(int key, int val) {
	    _key = key;
	    _val = val;
	}
    }

    Map<Integer, LinkedNode> _map;
    LinkedNode _head;
    LinkedNode _tail;
    int _capacity;

    public LruCache(int capacity) {
	_capacity = capacity;
	_map = new HashMap<Integer, LinkedNode>();
	_head = new LinkedNode(0, 0);
	_tail = new LinkedNode(0, 0);
	_head._next = _tail;
	_tail._pre = _head;
    }

    public int get(int key) {
	if(!_map.containsKey(key)) return -1;
	LinkedNode node = _map.get(key);
	moveToTail(node);

	return _tail._pre._val;
    }

    public void put(int key, int value) {
	if(_capacity <= 0) return;
	LinkedNode node = _map.get(key);
	if(node != null) {
	    moveToTail(node);
	    _tail._pre._val = value;
	} else {
	    if(_map.size() == _capacity) remove(_head._next, true);
	    node = new LinkedNode(key, value);
	    insertAfter(node, _tail._pre);
	    _map.put(key, _tail._pre);
	}
    }

    private LinkedNode remove(LinkedNode node, boolean removeEntry) {
	LinkedNode preNode = node._pre;
	LinkedNode nextNode = node._next;

	preNode._next = nextNode;
	nextNode._pre = preNode;
	node._next = null;
	node._pre = null;
	if(removeEntry) _map.remove(node._key);

	return node;
    }

    private void moveToTail(LinkedNode node) {
	if(_tail._pre == node) return;
	node = remove(node, false);
	insertAfter(node, _tail._pre);
    }

    //Insert insNode after node
    private void insertAfter(LinkedNode insNode, LinkedNode node) {
	LinkedNode nextNode = node._next;
	insNode._next = nextNode;
	nextNode._pre = insNode;
	node._next = insNode;
	insNode._pre = node;
    }
}