package com.quesofttech.web.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Conversations {
	
	private Map<String, Conversation> _conversationMap = new HashMap<String, Conversation>();
	
	public synchronized void add(Conversation conversation) {
		if (conversation.getTarget() == null) {
			throw new IllegalArgumentException("Obj must not be null.");
		}
		if (_conversationMap.containsKey(conversation.getId())) {
			throw new IllegalArgumentException("Conversation already exists. conversationId = " + conversation.getId());
		}
		_conversationMap.put(conversation.getId(), conversation);
	}
	
	public void remove(String conversationId) {
		Object obj = _conversationMap.remove(conversationId);
		if (obj == null) {
			throw new IllegalArgumentException("Conversation did not exist. conversationId = " + conversationId);
		}
	}
	
	public Conversation get(String conversationId) {
		return _conversationMap.get(conversationId);
	}
	
	public Collection<Conversation> getAll() {
		return _conversationMap.values();
	}

	public boolean isEmpty() {
		return _conversationMap.isEmpty();
	}

}
