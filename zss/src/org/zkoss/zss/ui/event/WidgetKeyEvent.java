/* WidgetKeyEvent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/5/20 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zss.ui.event;

import org.zkoss.zk.ui.Component;
import org.zkoss.zss.api.model.Sheet;
/**
 * Event about widget keystroke
 * @author dennis
 * @since 3.0.0
 */
public class WidgetKeyEvent extends org.zkoss.zk.ui.event.KeyEvent{
	private static final long serialVersionUID = 1L;
	private Sheet _sheet;
	private Object _data;

	public WidgetKeyEvent(String name, Component target, Sheet sheet, Object data,int keyCode,
			boolean ctrlKey, boolean shiftKey, boolean altKey) {
		super(name,target,keyCode,ctrlKey,shiftKey,altKey);
		_sheet = sheet;
		_data = data;
	}
	
	/**
	 * get Sheet
	 * @return sheet the related sheet 
	 */
	public Sheet getSheet(){
		return _sheet;
	}
	
	@Override
	public Object getData(){
		return _data;
	}
	
}
