package com.trade.store.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.trade.store.exception.TradeException;
import com.trade.store.model.Trade;
import com.trade.store.model.TradePK;
import com.trade.store.service.TradeServiceImpl;

public class ExcelHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ExcelHelper.class);
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Trade Id", "Version", "Counter-Party Id", "Book-Id","Maturity Date","Created Date", "Expired"};
	  static String SHEET = "trades";// name of sheet to be uploaded 

	  public static boolean hasExcelFormat(MultipartFile file) {
		LOGGER.info("Checking file format");
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<Trade> getTradeList(InputStream is) {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);

	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();
	      
	      List<Trade> tradeList = new ArrayList<Trade>();

	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();

	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }

	        Iterator<Cell> cellsInRow = currentRow.iterator();

	        Trade trade = new Trade();
	        TradePK pk=new TradePK();
	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();

	          switch (cellIdx) {
	          case 0:
	        	 pk.setTradeId(currentCell.getStringCellValue());
	            break;

	          case 1:
	        	  pk.setVersion((int) currentCell.getNumericCellValue());
	        	  trade.setTradePk(pk);
	            break;

	          case 2:
	            trade.setCounterPartyId(currentCell.getStringCellValue());
	            break;

	          case 3:
	            trade.setBookId(currentCell.getStringCellValue());
	            break;
	            
	          case 4:
	        	  	Date matDate=currentCell.getDateCellValue();
	        	  	Date utilDate=new Date();
	        	  	java.sql.Date sqlDate = new java.sql.Date(matDate.getTime());
	        	  	java.sql.Date currentDate = new java.sql.Date(utilDate.getTime());
	        	  	/*
					 * Store should not allow the trade which has less maturity date then today
					 * date.
					 */
	        	  	if(!matDate.before(currentDate)) {
	        	  		trade.setMaturityDate(sqlDate);
			            break;
	        	  	}else {
	        	  		throw new TradeException("Maturity date should not be less that current date"); 
	        	  	}
	        	  	
		            
	          case 5:
	        	  	Date createDate=currentCell.getDateCellValue();
	        	  	java.sql.Date createdt = new java.sql.Date(createDate.getTime());
	        	  	trade.setCreatedDate(createdt);
	        	    break;
		            
	          default:
	            break;
	          }

	          cellIdx++;
	        }

	        tradeList.add(trade);
	      }
	      LOGGER.info("Excel data converted to list..");
	      workbook.close();
	      
	      return tradeList;
	    } catch (IOException e) {
	      throw new RuntimeException(ConstantsVar.PARSEFILE_FAILED + e.getMessage());
	    }
	  }

}
