package org.zkoss.zss.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.zkoss.zss.api.CellOperationUtil.applyFontBoldweight;
import static org.zkoss.zss.api.Ranges.range;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.poi.xssf.usermodel.XSSFComment;
import org.zkoss.poi.xssf.usermodel.XSSFSheet;
import org.zkoss.zss.Setup;
import org.zkoss.zss.api.CellOperationUtil;
import org.zkoss.zss.api.Exporter;
import org.zkoss.zss.api.Exporters;
import org.zkoss.zss.api.Importers;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Range.DeleteShift;
import org.zkoss.zss.api.Range.InsertCopyOrigin;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.Range.InsertShift;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.EditableCellStyle;
import org.zkoss.zss.api.model.EditableFont;
import org.zkoss.zss.api.model.Font;
import org.zkoss.zss.api.model.CellStyle.BorderType;
import org.zkoss.zss.api.model.Sheet;

/**
 * ZSS-408.
 * ZSS-414. ZSS-415.
 * ZSS-418.
 * ZSS-425. ZSS-426.
 * ZSS-435.
 * ZSS-439.
 * @author kuro
 *
 */
public class Issue400Test {
	
	@BeforeClass
	public static void setUpLibrary() throws Exception {
		Setup.touch();
	}
	
	@Before
	public void startUp() throws Exception {
		ZssContext.setThreadLocal(new ZssContext(Locale.TAIWAN,-1));
	}
	
	@After
	public void tearDown() throws Exception {
		ZssContext.setThreadLocal(null);
	}

	@Test
	public void testZSS437() throws IOException, URISyntaxException {
		
		String EXPORT_FILE = "book/zss437.xlsx";
		
		Book workbook = Util.loadBook("book/blank.xlsx");
		Sheet sheet = workbook.getSheet("Sheet1");
		Range column0 = range(sheet, "A1");
		Util.export(workbook, EXPORT_FILE);
		
		column0.setCellEditText("Bold");
		applyFontBoldweight(column0, Font.Boldweight.BOLD);
		column0.setColumnWidth(100);
		assertEquals(100, sheet.getColumnWidth(0));
		
		//load saved file to validate width is saved
		Util.export(workbook, EXPORT_FILE);
		workbook = Util.loadBook(EXPORT_FILE);
		sheet = workbook.getSheet("Sheet1");
		assertEquals(100, sheet.getColumnWidth(0));
		//for human eye checking
		Util.open(EXPORT_FILE);
		
	}
	
	/**
	 * Endless loop when export demo swineFlu.xls pdf
	 */
	@Test
	public void testZSS426() throws IOException {
		
		final String filename = "book/426-swineFlu.xls";
		final InputStream is =  getClass().getResourceAsStream(filename);
		Book workbook = Importers.getImporter().imports(is, filename);
		
		// export work book
    	Exporter exporter = Exporters.getExporter();
    	java.io.File temp = java.io.File.createTempFile("test",".xls");
    	java.io.FileOutputStream fos = new java.io.FileOutputStream(temp);
    	exporter.export(workbook,fos);
    	
    	// import book again
    	Importers.getImporter().imports(temp,"test");

	}
	
	/**
	 * insert whole row or column when overlap merge cell with border style will cause unexpected result
	 */
	@Ignore
	@Test
	public void testZSS435() throws IOException {
		final String filename = "book/blank.xlsx";
		final InputStream is =  getClass().getResourceAsStream(filename);
		Book workbook = Importers.getImporter().imports(is, filename);
		
		Sheet sheet = workbook.getSheet("Sheet1");
		
		Range range = Ranges.range(sheet, "B2:D4");
		range.merge(false);
		
		Range columnC = Ranges.range(sheet, "C1");
		columnC.toColumnRange().insert(InsertShift.DEFAULT, InsertCopyOrigin.FORMAT_NONE);
		
		assertEquals(BorderType.THIN, columnC.getCellStyle().getBorderBottom());
		
	}
	
	/**
	 * Cannot save 2003 format if the file contains auto filter configuration.
	 */
	@Test
	public void testZSS408() throws IOException {
		
		final String filename = "book/408-save-autofilter.xls";
		final InputStream is =  getClass().getResourceAsStream(filename);
		Book workbook = Importers.getImporter().imports(is, filename);
		
		// export work book
    	Exporter exporter = Exporters.getExporter();
    	java.io.File temp = java.io.File.createTempFile("test",".xls");
    	java.io.FileOutputStream fos = new java.io.FileOutputStream(temp);
    	exporter.export(workbook,fos);
    	
    	// import book again
    	Importers.getImporter().imports(temp,"test");

	}
	
	/**
	 * 1. load a blank sheet (excel 2003)
	 * 2. set cell empty string to any cell will cause exception
	 */
	@Test
	public void testZSS414() throws IOException {
		final String filename = "book/blank.xls";
		final InputStream is =  getClass().getResourceAsStream(filename);
		Book workbook = Importers.getImporter().imports(is, filename);
		Sheet sheet1 = workbook.getSheet("Sheet1");
		Range r = Ranges.range(sheet1, "C1");
		r.setCellEditText("");
	}
	
	/**
	 * 1. import a book with comment
	 * 2. export
	 * 3. import again will throw exception
	 */
	@Test
	public void testZSS415() throws IOException {
		
		final String filename = "book/415-commentUnsupport.xls";
		final InputStream is =  getClass().getResourceAsStream(filename);
		Book workbook = Importers.getImporter().imports(is, filename);
		
		// export work book
    	Exporter exporter = Exporters.getExporter();
    	java.io.File temp = java.io.File.createTempFile("test",".xls");
    	java.io.FileOutputStream fos = new java.io.FileOutputStream(temp);
    	exporter.export(workbook, fos);
    	
    	// import book again
    	Importers.getImporter().imports(temp, "test");
	}
	
	@Test
	public void testZSS425() throws IOException {
		
		final String filename = "book/425-updateStyle.xlsx";
		final InputStream is = getClass().getResourceAsStream(filename);
		Book book = Importers.getImporter().imports(is, filename);
		
		Range r = Ranges.range(book.getSheetAt(0),0,0);
		
		CellOperationUtil.applyBackgroundColor(r, "#f0f000");
		r = Ranges.range(book.getSheetAt(0),0,0);
		Assert.assertEquals("#f0f000", r.getCellStyle().getBackgroundColor().getHtmlColor());
		
		
		// export work book
    	Exporter exporter = Exporters.getExporter();
    	
    	java.io.File temp = java.io.File.createTempFile("test",".xlsx");
    	java.io.FileOutputStream fos = new java.io.FileOutputStream(temp);
    	//export first time
    	exporter.export(book, fos);
    	
		CellOperationUtil.applyBackgroundColor(r, "#00ff00");
		r = Ranges.range(book.getSheetAt(0),0,0);
		//change again
		Assert.assertEquals("#00ff00", r.getCellStyle().getBackgroundColor().getHtmlColor());
		
    	fos = new java.io.FileOutputStream(temp);
    	//export 2nd time
    	exporter.export(book, fos);
    	System.out.println(">>>write "+temp);
    	// import book again
    	book = Importers.getImporter().imports(temp, "test");
    	r = Ranges.range(book.getSheetAt(0),0,0);
    	//get #ff0000 if bug is not fixed
		Assert.assertEquals("#00ff00", r.getCellStyle().getBackgroundColor().getHtmlColor());
	}
	
	@Test
	public void testZSS432() throws IOException {
		
//		Book book = Util.loadBook("book/blank.xlsx");
		Book book = Util.loadBook("book/432.xlsx");
		
		Sheet sheet = Ranges.range(book.getSheetAt(0)).createSheet("newone");
		
		File temp = Setup.getTempFile();
		//export first time
		Exporters.getExporter().export(book, temp);
		
		sheet = book.getSheet("newone");
		
		//edit the new sheet
		Ranges.range(sheet,"A1").setCellEditText("ABC");
		
		//export again
		Exporters.getExporter().export(book, temp);
		
		//import, should't get any error
		Importers.getImporter().imports(temp, "test");
		
		sheet = book.getSheet("newone");
		
		//verify the value
		Assert.assertEquals("ABC", Ranges.range(sheet,"A1").getCellEditText());
	}
	
	
	@Test
	public void testZSS430() throws IOException {
		
		Book book = Util.loadBook("book/430-export-formula.xlsx");
		Sheet sheet = book.getSheet("formula-math");
		Assert.assertEquals("2.09", Ranges.range(sheet,"C7").getCellFormatText());
		
		
		File temp = Setup.getTempFile();
		//export first time
		Exporters.getExporter().export(book, temp);

		//import, should't get any error
		Importers.getImporter().imports(temp, "test");
		
		sheet = book.getSheet("formula-math");
		Assert.assertEquals("2.09", Ranges.range(sheet,"C7").getCellFormatText());
		
		System.out.println(">>>>"+temp);
		//can't be opened by excel
		//how to 
	}
	
	@Test
	public void testZSS429() throws IOException {
		
		Book book = Util.loadBook("book/429-autofilter.xlsx");
		Sheet sheet = book.getSheetAt(0);
		Assert.assertEquals("A", Ranges.range(sheet,"C4").getCellFormatText());
		
		File temp = Setup.getTempFile();
		//export first time
		Exporters.getExporter().export(book, temp);
		
		//shouln't cause exception
		Ranges.range(sheet,"C4").toColumnRange().insert(InsertShift.RIGHT, InsertCopyOrigin.FORMAT_LEFT_ABOVE);
		
		Assert.assertEquals("A", Ranges.range(sheet,"D4").getCellFormatText());
		
		Exporters.getExporter().export(book, temp);
		
		
		//import, should't get any error
		Importers.getImporter().imports(temp, "test");
				
		sheet = book.getSheetAt(0);
		Assert.assertEquals("A", Ranges.range(sheet,"D4").getCellFormatText());
		
	}

	@Test 
	public void testZSS431() throws IOException {
		testZSS431_0(Util.loadBook("book/431-freezepanel.xlsx"),7,4);
		testZSS431_0(Util.loadBook("book/431-freezepanel.xlsx"),7,0);
		testZSS431_0(Util.loadBook("book/431-freezepanel.xlsx"),0,4);
		testZSS431_0(Util.loadBook("book/431-freezepanel.xls"),7,4);
		testZSS431_0(Util.loadBook("book/431-freezepanel.xls"),7,0);
		testZSS431_0(Util.loadBook("book/431-freezepanel.xls"),0,4);
	}
	
	public void testZSS431_0(Book book,int row, int column) throws IOException {
		Sheet sheet = book.getSheetAt(0);
		Assert.assertEquals(0, sheet.getRowFreeze());
		Assert.assertEquals(0, sheet.getColumnFreeze());
		
		Ranges.range(sheet).setFreezePanel(row, column);
		Assert.assertEquals(row, sheet.getRowFreeze());
		Assert.assertEquals(column, sheet.getColumnFreeze());
		
		Assert.assertEquals(0, sheet.getPoiSheet().getTopRow());
		Assert.assertEquals(0, sheet.getPoiSheet().getLeftCol());
		
		File temp = Setup.getTempFile();
		//export first time
		Exporters.getExporter().export(book, temp);
		

		book = Importers.getImporter().imports(temp, "test");
		sheet = book.getSheetAt(0);
		
		Assert.assertEquals(row, sheet.getRowFreeze());
		Assert.assertEquals(column, sheet.getColumnFreeze());
		Assert.assertEquals(0, sheet.getPoiSheet().getTopRow());
		Assert.assertEquals(0, sheet.getPoiSheet().getLeftCol());
		
		//TODO how to verify it in Excel?
	}

	@Test
	public void testZSS439() throws IOException {
		
		final String filename = "book/439-rows.xlsx";
		final InputStream is = getClass().getResourceAsStream(filename);
		Book book = Importers.getImporter().imports(is, filename);
		
		// fill text on 3 rows
		Sheet sheet = book.getSheetAt(0);
		Ranges.range(sheet, "A1:A3").setCellEditText("test");
		
		// apply bold style on such rows
		Range r = Ranges.range(sheet, "A1:A3");
		EditableFont f = r.getCellStyleHelper().createFont(null);
		f.setBoldweight(org.zkoss.zss.api.model.Font.Boldweight.BOLD);
		EditableCellStyle s = r.getCellStyleHelper().createCellStyle(null);
		s.setFont(f);
		r.setCellStyle(s);
		
		// remove two rows
		Ranges.range(sheet, "1:2").toRowRange().delete(DeleteShift.UP);
		
		// read the style, it should not occur exception.
		Ranges.range(sheet, "A1").getCellStyle();
	}
	
	@Test
	public void testZSS412() throws IOException {
		
		Book book = Util.loadBook("book/412-overlap-undo.xls");
		Sheet sheet = book.getSheetAt(0);
		
		Assert.assertEquals(1, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("A2:A3", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		
		Range r1 = Ranges.range(sheet,"A1:A3");
		Range r2 = Ranges.range(sheet,"A2");
		r1.paste(r2);	
		
		Assert.assertEquals(1, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("A3:A4", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		
		Range r3 = Ranges.range(sheet,"A2:A3");
		r3.merge(false);
		Assert.assertEquals(1, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("A2:A3", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		
		r3 = Ranges.range(sheet,"A2:B3");
		r3.merge(true);
		Assert.assertEquals(2, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("A2:B2", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		Assert.assertEquals("A3:B3", sheet.getPoiSheet().getMergedRegion(1).formatAsString());
	}
	@Test
	public void testZSS412_395_1() throws IOException {
		Book book = Util.loadBook("book/blank.xls");
		Sheet sheet = book.getSheetAt(0);
		Range rangeA = Ranges.range(sheet, "H11:J13");
		rangeA.merge(false); // merge a 3 x 3
		
		Assert.assertEquals(1, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("H11:J13", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		
		Range rangeB = Ranges.range(sheet, "H12"); // a whole row cross the merged cell
		rangeB.toRowRange().unmerge(); // perform unmerge operation
	
		Assert.assertEquals(0, sheet.getPoiSheet().getNumMergedRegions());
		
		//again on column
		rangeA = Ranges.range(sheet, "H11:J13");
		rangeA.merge(false); // merge a 3 x 3
		Assert.assertEquals(1, sheet.getPoiSheet().getNumMergedRegions());
		Assert.assertEquals("H11:J13", sheet.getPoiSheet().getMergedRegion(0).formatAsString());
		
		rangeB = Ranges.range(sheet, "H12"); // a whole row cross the merged cell
		rangeB.toColumnRange().unmerge(); // perform unmerge operation
		Assert.assertEquals(0, sheet.getPoiSheet().getNumMergedRegions());
	}
	
	@Test
	public void testZSS418() throws IOException {
		Book book = Util.loadBook("book/418-comment.xlsx");
		Sheet sheet = book.getSheetAt(0);
		XSSFSheet ps = (XSSFSheet)sheet.getPoiSheet();
		
		// check original comments
		String[] refs = {"C3", "D3", "E3", "F3", "H3", "I3", "C4", "C5", "C6", "C8", "C9"};
		String[] txts = {"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10"}; // null indicates test comment not existed
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "G3").delete(DeleteShift.LEFT);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "H3", "C4", "C5", "C6", "C8", "C9", "I3"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "C7").delete(DeleteShift.UP);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "H3", "C4", "C5", "C6", "C7", "C8", "C9"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "F3").insert(InsertShift.RIGHT, InsertCopyOrigin.FORMAT_RIGHT_BELOW);
		refs = new String[]{"C3", "D3", "E3", "G3", "H3", "I3", "C4", "C5", "C6", "C7", "C8", "F3"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);
		
		Ranges.range(sheet, "C6").insert(InsertShift.DOWN, InsertCopyOrigin.FORMAT_LEFT_ABOVE);
		refs = new String[]{"C3", "D3", "E3", "G3", "H3", "I3", "C4", "C5", "C7", "C8", "C9", "C6"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);
		
		Ranges.range(sheet, "F").toColumnRange().delete(DeleteShift.LEFT);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "H3", "C4", "C5", "C7", "C8", "C9", "I3"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "6").toRowRange().delete(DeleteShift.UP);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "H3", "C4", "C5", "C6", "C7", "C8", "C9"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "G").toColumnRange().insert(InsertShift.RIGHT, InsertCopyOrigin.FORMAT_RIGHT_BELOW);
		refs = new String[]{"C3", "D3", "E3", "F3", "H3", "I3", "C4", "C5", "C6", "C7", "C8", "G3"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "7").toRowRange().insert(InsertShift.DOWN, InsertCopyOrigin.FORMAT_LEFT_ABOVE);
		refs = new String[]{"C3", "D3", "E3", "F3", "H3", "I3", "C4", "C5", "C6", "C8", "C9", "C7"};
		txts = new String[]{"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "H3:I3").shift(0, -2);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "C4", "C5", "C6", "C8", "C9", "H3", "I3"};
		txts = new String[]{"c0", "c1", "c2", "c4", "c5", "c6", "c7", "c8", "c9", "c10", null, null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "C8:C9").shift(-2, 0);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "C4", "C5", "C6", "C7", "C8", "C9"};
		txts = new String[]{"c0", "c1", "c2", "c4", "c5", "c6", "c7", "c9", "c10", null, null};
		testZSS418_0(ps, refs, txts);

		Ranges.range(sheet, "C3:G7").shift(-1, -1);
		refs = new String[]{"C3", "D3", "E3", "F3", "G3", "C4", "C5", "C6", "C7"};
		txts = new String[]{null, null, null, null, null, null, null, null, null};
		testZSS418_0(ps, refs, txts);
		refs = new String[]{"B2", "C2", "D2", "E2", "F2", "B3", "B4", "B5", "B6"};
		txts = new String[]{"c0", "c1", "c2", "c4", "c5", "c6", "c7", "c9", "c10"};
		testZSS418_0(ps, refs, txts);
		
		Ranges.range(sheet, "B2:F6").shift(2, 2);
		refs = new String[]{"B2", "C2", "D2", "E2", "F2", "B3", "B4", "B5", "B6"};
		txts = new String[]{null, null, null, null, null, null, null, null, null};
		testZSS418_0(ps, refs, txts);
		refs = new String[]{"D4", "E4", "F4", "G4", "H4", "D5", "D6", "D7", "D8"};
		txts = new String[]{"c0", "c1", "c2", "c4", "c5", "c6", "c7", "c9", "c10"};
		testZSS418_0(ps, refs, txts);
	}
	
	public void testZSS418_0(XSSFSheet sheet, String[] refs, String[] texts) {
		for(int i = 0; i < refs.length; ++i) {
			XSSFComment comment = Util.getComment(sheet, refs[i]);
			String text = texts[i];
			if(text != null) {
				Assert.assertNotNull(comment);
				Assert.assertEquals(texts[i], comment.getString().getString());
			} else {
				Assert.assertNull(comment);
			}
		}
	}	
}