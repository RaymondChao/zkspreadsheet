/* Events.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Dec 19, 2007 12:48:06 PM     2007, Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.zkoss.zss.ui.event;




/**
 * @author Dennis.Chen
 *
 */
public class Events {
	
	/** The onCellFocused event (used with {@link CellEvent}).
	 * Sent when cell get focus from client.
	 */
	public static final String ON_CELL_FOUCSED = "onCellFocused";
	
	/** The onStartEditing event (used with {@link CellEvent}).
	 * Sent when cell start editing.
	 */
	public static final String ON_START_EDITING = "onStartEditing";
	
	
	/** The onCellFocused event (used with {@link StopEditingEvent}).
	 * Sent when cell stop editing
	 */
	public static final String ON_STOP_EDITING = "onStopEditing";
	
	/**
	 * The onCellClick event (used with {@link CellMouseEvent}).
	 * Sent when user left click on a cell
	 */
	public static final String ON_CELL_CLICK = "onCellClick";
	
	/**
	 * The onCellRightClick event (used with {@link CellMouseEvent}).
	 * Sent when user right click on a cell
	 */
	public static final String ON_CELL_RIGHT_CLICK = "onCellRightClick";
	
	/**
	 * The onCellDoubleClick event (used with {@link CellMouseEvent}).
	 * Sent when user double click on a cell
	 */
	public static final String ON_CELL_DOUBLE_CLICK = "onCellDoubleClick";
	
	/**
	 * The onCellFilter event (used with {@link CellMouseEvent}).
	 * Sent when user click on the cell filter button.
	 */
	public static final String ON_CELL_FILTER = "onCellFilter";
	
	/**
	 * The onCellValidator event (used with {@link CellMouseEvent}).
	 * Sent when user click on the cell validation drop down button
	 */
	public static final String ON_CELL_VALIDATOR = "onCellValidator";
	
	/**
	 * The onHeaderClick event (used with {@link HeaderMouseEvent}).
	 * Sent when user left click on a header
	 */
	public static final String ON_HEADER_CLICK = "onHeaderClick";
	
	/**
	 * The onHeaderRightClick event (used with {@link HeaderMouseEvent}).
	 * Sent when user right click on a header
	 */
	public static final String ON_HEADER_RIGHT_CLICK = "onHeaderRightClick";
	
	/**
	 * The onHeaderDoubleClick event (used with {@link HeaderMouseEvent}).
	 * Sent when user double click on a header
	 */
	public static final String ON_HEADER_DOUBLE_CLICK = "onHeaderDoubleClick";
	
	/** 
	 * The onHeaderSzie event (used with {@link HeaderUpdateEvent}).
	 * Sent when user resize a header
	 */
	public static final String ON_HEADER_UPDATE = "onHeaderUpdate";
	
	/**
	 * The onCellSelection event (used with {@link CellSelectionEvent}).
	 * Sent when user select a row/column or a range of cells
	 */
	public static final String ON_CELL_SELECTION = "onCellSelection";
	
	/**
	 * The onCellSelectionUpdate event (used with {@link CellSelectionUpdateEvent}).
	 * Sent when user move or modify the range of a selection
	 */
	public static final String ON_CELL_SELECTION_UPDATE = "onCellSelectionUpdate";
	
	/**
	 * The onEditboxEditing event (used with {@link EditboxEditingEvent}).
	 * Sent when user start to typing in the ZSSEditbox
	 */
	public static final String ON_EDITBOX_EDITING = "onEditboxEditing";
	
	/**
	 * The onHyperlink event (used with {@link HyperlinkEvent}).
	 * Sent when user click on the hyperlink of a cell.
	 */
	public static final String ON_HYPERLINK = "onHyperlink";
	/**
	 * The onSheetSelect event
	 * Sent when sheet is selected.
	 */
	public static final String ON_SHEET_SELECTED = "onSheetSelected";
	
	/**
	 * The onCtrlKey event (used with {@link KeyEvent})
	 */
	public static final String ON_CTRL_KEY = org.zkoss.zk.ui.event.Events.ON_CTRL_KEY;
	
	/**
	 * The onAuxAction event (used with {@link AuxActionEvent})
	 */
	public static final String ON_AUX_ACTION = "onAuxAction";
	
	
	/**
	 * The onWidgetCtrlKey event (used with {@link WidgetKeyEvent})
	 */
	public static final String ON_WIDGET_CTRL_KEY = "onWidgetCtrlKey";
	
	/**
	 * The onWidgetUpdate event (used with {@link WidgetUpdateEvent})
	 */
	public static final String ON_WIDGET_UPDATE = "onWidgetUpdate";//"onZSSMoveWidget";
	
	/* 
	 * Following are events that fire by book SSDataEvent and delegate to Sparedsheet to provide to component user.
	 * TODO consider to let user register listener on book directly or wrap more event 
	 */

//	/**
//	 * The onSheetNameChange event.
//	 * Sent when sheet is deleted.
//	 */
//	public static final String ON_SHEET_NAME_CHANGE = "onSheetNameChange";
//	/**
//	 * The onSheetOrderChange event.
//	 * Sent when sheet is deleted.
//	 */
//	public static final String ON_SHEET_ORDER_CHANGE = "onSheetOrderChange";
//	
//	/**
//	 * The onSheetDelete event.
//	 * Sent when sheet is deleted.
//	 */
//	public static final String ON_SHEET_DELETE = "onSheetDelete";
//
//	/**
//	 * The onSheetCreate event.
//	 * Sent when sheet is deleted.
//	 */
//	public static final String ON_SHEET_CREATE = "onSheetCreate";
//		
//	/** The onCellChange event (used with {@link CellEvent}).
//	 * Sent when cell contents changed.
//	 */
//	public static final String ON_CELL_CHANGE = "onCellChange";
	
	/*
	 * end of book delegation event  
	 */
	
}
