package com.quesoware.web.common;

public class Conversation {
	private String _id;
	private Object _target;
	
	public Conversation(String id, Object target) {
		_id = id;
		_target = target;
	}
	
	public String getId() {
		return _id;
	}

	public Object getTarget() {
		return _target;
	}
}
