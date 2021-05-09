package com.trade.store.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trade.store.StoreTradesApplication;
import com.trade.store.exception.TradeException;
import com.trade.store.helper.ConstantsVar;
import com.trade.store.helper.ExcelHelper;
import com.trade.store.model.Trade;
import com.trade.store.model.TradePK;
import com.trade.store.repository.TradeRepository;

@Service
public class TradeServiceImpl implements TradeService {
	private static final Logger LOGGER=LoggerFactory.getLogger(TradeServiceImpl.class);
	@Autowired
	 TradeRepository repository;
	
	 public void save(MultipartFile file) {
		    try {
		      List<Trade> trades = ExcelHelper.getTradeList(file.getInputStream());
		      LOGGER.info("Checking if lower version of trade exists");
		      checkIfLowerVersionExists(trades);
		      repository.saveAll(trades);
		    } catch (IOException e) {
		      throw new RuntimeException(ConstantsVar.FILE_STORE_FAILED + e.getMessage());
		    }
		  }
	 private void checkIfLowerVersionExists(List<Trade> trades) {
		// TODO Auto-generated method stub
		 List<TradePK> versionList=repository.findByVersion();
		 
		 for (TradePK tradePK : versionList) {
			for(Trade trade: trades) {
				TradePK pk=trade.getTradePk();
				if(pk.getTradeId().equalsIgnoreCase(tradePK.getTradeId())) {
					if(pk.getVersion()<tradePK.getVersion()) {
						LOGGER.info("Lower version of existing trade is received");
						throw new TradeException(ConstantsVar.LOWER_VERSION);
					}
				}
			}
		}
		 
	}
	public List<Trade> getAllTrades() {
		    return (List<Trade>) repository.findAll();
		  }
	
}
