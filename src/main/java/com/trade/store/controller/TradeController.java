package com.trade.store.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trade.store.model.TradePK;
import com.trade.store.StoreTradesApplication;
import com.trade.store.exception.TradeException;
import com.trade.store.helper.ConstantsVar;
import com.trade.store.helper.ExcelHelper;
import com.trade.store.helper.ResponseMessage;
import com.trade.store.model.Trade;
import com.trade.store.repository.TradeRepository;
import com.trade.store.service.TradeService;

@RestController
@RequestMapping("/trade")
public class TradeController {
	private static final Logger LOGGER=LoggerFactory.getLogger(TradeController.class);
	private TradeRepository repository;
	
	@Autowired
	public TradeController(TradeRepository repository) {
		this.repository = repository;
	}

	@Autowired
	TradeService service;
	
	@PostConstruct
    public void doLog() {
		//To update the expired flag if maturity date is crossed
        //logger.info("Info message in MyController");
		Date currentDate=new Date();
		java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
		repository.autoUpdateFlag(sqlDate);
    }
	
	
	@RequestMapping(value="/getTrades", method=RequestMethod.GET)
	public List<Trade> getAllTrade(){
		return (List<Trade>) repository.findAll();
	}
	
	@RequestMapping(value="/getTrade(id)")
	public Trade getTrade(@PathVariable("id") TradePK pk) {
		return repository.findById(pk).get();
	}
	
	//Upload excel file using below link
	//localhost:8080/storeTrades/trade/uploadTradeFile
	  @PostMapping("/uploadTradeFile")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
	    	  LOGGER.info("Inside TradeController -> uploadFile method");
	        service.save(file);
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(ConstantsVar.UPLOAD_SUCCESS + file.getOriginalFilename()));
	      }catch(TradeException tr){
	    	  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(tr.getMessage()));
	      }catch (Exception e) { 
	    	  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
			  ResponseMessage(ConstantsVar.UPLOAD_FAILED + file.getOriginalFilename() + "!")); }
			 
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(ConstantsVar.NO_FILE));
	  }
	
}
