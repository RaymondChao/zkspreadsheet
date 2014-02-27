/* StyleUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jun 16, 2008 2:50:27 PM     2008, Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package org.zkoss.zss.range.impl;

import org.zkoss.zss.model.SBook;
import org.zkoss.zss.model.SCell;
import org.zkoss.zss.model.SCellStyle;
import org.zkoss.zss.model.SColor;
import org.zkoss.zss.model.SFont;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.util.CellStyleMatcher;
import org.zkoss.zss.model.util.FontMatcher;
/**
 * A utility class to help spreadsheet set style of a cell
 * @author Dennis.Chen
 *
 */
public class StyleUtil {
//	private static final Log log = Log.lookup(NStyles.class);
	
	public static SCellStyle cloneCellStyle(SCell cell) {
		final SCellStyle destination = cell.getSheet().getBook().createCellStyle(cell.getCellStyle(), true);
		return destination;
	}
	
	public static void setFontColor(SSheet sheet, int row, int col, String color/*,HashMap<Integer,NCellStyle> cache*/){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		final SColor orgColor = orgFont.getColor();
		final SColor newColor = book.createColor(color);
		if (orgColor == newColor || orgColor != null && orgColor.equals(newColor)) {
			return;
		}
		
//		NCellStyle hitStyle = cache==null?null:cache.get((int)orgStyle.getIndex());
//		if(hitStyle!=null){
//			cell.setCellStyle(hitStyle);
//			return;
//		}
		
		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setColor(color);
		
		SFont font = book.searchFont(fontmatcher);
		
		
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setColor(newColor);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
		
//		if(cache!=null){
//			cache.put((int)orgStyle.getIndex(), style);
//		}
	}
	
	
	public static void setFillColor(SSheet sheet, int row, int col, String htmlColor){
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		final SColor orgColor = orgStyle.getFillColor();
		final SColor newColor = book.createColor(htmlColor);
		if (orgColor == newColor || orgColor != null  && orgColor.equals(newColor)) { //no change, skip
			return;
		}
		
		CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
		matcher.setFillColor(htmlColor);
		matcher.setFillPattern(SCellStyle.FillPattern.SOLID_FOREGROUND);
		
		SCellStyle style = book.searchCellStyle(matcher);
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFillColor(newColor);
			style.setFillPattern(SCellStyle.FillPattern.SOLID_FOREGROUND);
		}
		cell.setCellStyle(style);
		
	}
	
	public static void setTextWrap(SSheet sheet,int row,int col,boolean wrap){
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		final boolean textWrap = orgStyle.isWrapText();
		if (wrap == textWrap) { //no change, skip
			return;
		}
		
		CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
		matcher.setWrapText(wrap);
		SCellStyle style = book.searchCellStyle(matcher);
		if(style==null){
			style  = cloneCellStyle(cell);
			style.setWrapText(wrap);
		}
		cell.setCellStyle(style);
	}
	
	public static void setFontHeightPoints(SSheet sheet,int row,int col,int fontHeightPoints){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final int orgSize = orgFont.getHeightPoints();
		if (orgSize == fontHeightPoints) { //no change, skip
			return;
		}
		
		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setHeightPoints(fontHeightPoints);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setHeightPoints(fontHeightPoints);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
	}
	
	public static void setFontStrikethrough(SSheet sheet,int row,int col, boolean strikeout){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final boolean orgStrikeout = orgFont.isStrikeout();
		if (orgStrikeout == strikeout) { //no change, skip
			return;
		}

		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setStrikeout(strikeout);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setStrikeout(strikeout);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
		
	}
	
	public static void setFontName(SSheet sheet,int row,int col,String name){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final String orgName = orgFont.getName();
		if (orgName.equals(name)) { //no change, skip
			return;
		}
		
		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setName(name);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setName(name);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
		
	}
	
	public static final short BORDER_EDGE_BOTTOM		= 0x01;
	public static final short BORDER_EDGE_RIGHT			= 0x02;
	public static final short BORDER_EDGE_TOP			= 0x04;
	public static final short BORDER_EDGE_LEFT			= 0x08;
	public static final short BORDER_EDGE_ALL			= BORDER_EDGE_BOTTOM|BORDER_EDGE_RIGHT|BORDER_EDGE_TOP|BORDER_EDGE_LEFT;
	
	public static void setBorder(SSheet sheet,int row,int col, String color, SCellStyle.BorderType linestyle){
		setBorder(sheet,row,col, color, linestyle, BORDER_EDGE_ALL);
	}
	
	public static void setBorderTop(SSheet sheet,int row,int col,String color, SCellStyle.BorderType linestyle){
		setBorder(sheet,row,col, color, linestyle, BORDER_EDGE_TOP);
	}
	public static void setBorderLeft(SSheet sheet,int row,int col,String color, SCellStyle.BorderType linestyle){
		setBorder(sheet,row,col, color, linestyle, BORDER_EDGE_LEFT);
	}
	public static void setBorderBottom(SSheet sheet,int row,int col,String color, SCellStyle.BorderType linestyle){
		setBorder(sheet,row,col, color, linestyle, BORDER_EDGE_BOTTOM);
	}
	public static void setBorderRight(SSheet sheet,int row,int col,String color, SCellStyle.BorderType linestyle){
		setBorder(sheet,row,col, color, linestyle, BORDER_EDGE_RIGHT);
	}
	
	public static void setBorder(SSheet sheet,int row,int col, String htmlColor, SCellStyle.BorderType lineStyle, short at){
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		//ZSS-464 try to search existed matched style
		SCellStyle style = null;
		final SColor color = book.createColor(htmlColor);
		boolean hasBorder = lineStyle != SCellStyle.BorderType.NONE;
		if(htmlColor!=null){
			final SCellStyle oldstyle = cell.getCellStyle();
			CellStyleMatcher matcher = new CellStyleMatcher(oldstyle);
			if((at & BORDER_EDGE_LEFT)!=0) {
				if(hasBorder)
					matcher.setBorderLeftColor(htmlColor);
				else
					matcher.removeBorderLeftColor();
				
				matcher.setBorderLeft(lineStyle);
			}
			if((at & BORDER_EDGE_TOP)!=0){
				if(hasBorder) 
					matcher.setBorderTopColor(htmlColor);
				else
					matcher.removeBorderTopColor();
				
				matcher.setBorderTop(lineStyle);
			}
			if((at & BORDER_EDGE_RIGHT)!=0){
				if(hasBorder)
					matcher.setBorderRightColor(htmlColor);
				else
					matcher.removeBorderRightColor();
				
				matcher.setBorderRight(lineStyle);
			}
			if((at & BORDER_EDGE_BOTTOM)!=0){
				if(hasBorder)
					matcher.setBorderBottomColor(htmlColor);
				else
					matcher.removeBorderBottomColor();
				
				matcher.setBorderBottom(lineStyle);
			}
			style = book.searchCellStyle(matcher);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			if((at & BORDER_EDGE_LEFT)!=0) {
				if(hasBorder)
					style.setBorderLeftColor(color);
				style.setBorderLeft(lineStyle);
			}
			if((at & BORDER_EDGE_TOP)!=0){
				if(hasBorder)
					style.setBorderTopColor(color);
				style.setBorderTop(lineStyle);
			}
			if((at & BORDER_EDGE_RIGHT)!=0){
				if(hasBorder)
					style.setBorderRightColor(color);
				style.setBorderRight(lineStyle);
			}
			if((at & BORDER_EDGE_BOTTOM)!=0){
				if(hasBorder)
					style.setBorderBottomColor(color);
				style.setBorderBottom(lineStyle);
			}
		}
		
		cell.setCellStyle(style);
	}
	
//	private static void debugStyle(String msg,int row, int col, Workbook book, NCellStyle style){
//		StringBuilder sb = new StringBuilder(msg);
//		sb.append("[").append(Ranges.getCellRefString(row, col)).append("]");
//		sb.append("Top:[").append(style.getBorderTop()).append(":").append(BookHelper.colorToBorderHTML(book,style.getTopBorderColorColor())).append("]");
//		sb.append("Left:[").append(style.getBorderLeft()).append(":").append(BookHelper.colorToBorderHTML(book,style.getLeftBorderColorColor())).append("]");
//		sb.append("Bottom:[").append(style.getBorderBottom()).append(":").append(BookHelper.colorToBorderHTML(book,style.getBottomBorderColorColor())).append("]");
//		sb.append("Right:[").append(style.getBorderRight()).append(":").append(BookHelper.colorToBorderHTML(book,style.getRightBorderColorColor())).append("]");
//		System.out.println(">>"+sb.toString());
//	}
	
	public static void setFontBoldWeight(SSheet sheet,int row,int col,SFont.Boldweight boldWeight){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final SFont.Boldweight orgBoldWeight = orgFont.getBoldweight();
		if (orgBoldWeight.equals(boldWeight)) { //no change, skip
			return;
		}
		
		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setBoldweight(boldWeight);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setBoldweight(boldWeight);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
	}
	
	public static void setFontItalic(SSheet sheet, int row, int col, boolean italic) {
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final boolean orgItalic = orgFont.isItalic();
		if (orgItalic == italic) { //no change, skip
			return;
		}

		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setItalic(italic);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setItalic(italic);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
		
	}
	
	public static void setFontUnderline(SSheet sheet,int row,int col, SFont.Underline underline){
		final SCell cell = sheet.getCell(row,col);
		final SBook book = sheet.getBook();
		final SCellStyle orgStyle = cell.getCellStyle();
		SFont orgFont = orgStyle.getFont();
		
		final SFont.Underline orgUnderline = orgFont.getUnderline();
		if (orgUnderline.equals(underline)) { //no change, skip
			return;
		}
		
		FontMatcher fontmatcher = new FontMatcher(orgFont);
		fontmatcher.setUnderline(underline);
		
		SFont font = book.searchFont(fontmatcher);
		
		SCellStyle style = null;
		if(font!=null){//search it since we have existed font
			CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
			matcher.setFont(font);
			style = book.searchCellStyle(matcher);
		}else{
			font = book.createFont(orgFont,true);
			font.setUnderline(underline);
		}
		
		if(style==null){
			style = cloneCellStyle(cell);
			style.setFont(font);
		}
		cell.setCellStyle(style);
	}
	
	public static void setTextHAlign(SSheet sheet,int row,int col, SCellStyle.Alignment align){
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		final SCellStyle.Alignment orgAlign = orgStyle.getAlignment();
		if (align.equals(orgAlign)) { //no change, skip
			return;
		}
		
		CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
		matcher.setAlignment(align);
		SCellStyle style = book.searchCellStyle(matcher);
		if(style==null){
			style = cloneCellStyle(cell);
			style.setAlignment(align);
		}
		cell.setCellStyle(style);
	}
	
	public static void setTextVAlign(SSheet sheet,int row,int col, SCellStyle.VerticalAlignment valign){
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		final SCellStyle.VerticalAlignment orgValign = orgStyle.getVerticalAlignment();
		if (valign.equals(orgValign)) { //no change, skip
			return;
		}

		CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);
		matcher.setVerticalAlignment(valign);
		SCellStyle style = book.searchCellStyle(matcher);
		if(style==null){
			style = cloneCellStyle(cell);
			style.setVerticalAlignment(valign);
		}
		cell.setCellStyle(style);

	}
	
	public static void setDataFormat(SSheet sheet, int row, int col, String format/*,HashMap<Integer,CellStyle> cache*/) {
		final SBook book = sheet.getBook();
		final SCell cell = sheet.getCell(row,col);
		final SCellStyle orgStyle = cell.getCellStyle();
		final String orgFormat = orgStyle.getDataFormat();
		if (format == orgFormat || (format!=null && format.equals(orgFormat))) { //no change, skip
			return;
		}
//		NCellStyle hitStyle = cache==null?null:cache.get((int)orgStyle.getIndex());
//		if(hitStyle!=null){
//			cell.setCellStyle(hitStyle);
//			return;
//		}
//		
		CellStyleMatcher matcher = new CellStyleMatcher(orgStyle);

		matcher.setDataFormat(format);
		SCellStyle style = book.searchCellStyle(matcher);
		if(style==null){
			style = cloneCellStyle(cell);
			style.setDataFormat(format);
		}
		cell.setCellStyle(style);
//		if(cache!=null){
//			cache.put((int)orgStyle.getIndex(), style);
//		}
		
	}
}