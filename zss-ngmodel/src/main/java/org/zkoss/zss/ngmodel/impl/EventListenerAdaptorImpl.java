/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/12/01 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.ngmodel.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zss.ngmodel.ModelEvent;
import org.zkoss.zss.ngmodel.ModelEventListener;
/**
 * 
 * @author dennis
 *
 */
public class EventListenerAdaptorImpl implements EventListenerAdaptor,Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private final List<ModelEventListener> listeners = new LinkedList<ModelEventListener>();

	@Override
	public void addEventListener(ModelEventListener listener) {
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}
	}

	@Override
	public void removeEventListener(ModelEventListener listener) {
		if(listeners!=null){
			listeners.remove(listener);
		}
	}

	@Override
	public void sendModelEvent(ModelEvent event) {
		for(ModelEventListener l:listeners){
			l.onEvent(event);
		}
	}

	@Override
	public int size() {
		return listeners.size();
	}

	@Override
	public void clear() {
		listeners.clear();
	}

}
