package com.trade.store;


import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trade.store.model.TradePK;
import com.trade.store.model.Trade;
import com.trade.store.repository.TradeRepository;

@SpringBootTest
class StoreTradesApplicationTests {
	@Autowired
	private TradeRepository repo;
	
	@Test
	void testCreateTradeStore() {
		Date currentDate=new Date(0);
		String str="2019-03-31";
		TradePK pk=new TradePK();
		pk.setTradeId("T1");
		pk.setVersion(1);
		Trade trade=new Trade();
		trade.setTradePk(pk);
		trade.setCounterPartyId("CP-1");
		trade.setBookId("B2");
		trade.setMaturityDate(currentDate.valueOf(str));
		trade.setCreatedDate(currentDate);
		trade.setExpired("N");
		repo.save(trade);
	}

	@Test
	void testFindTradeById() {
		Trade trade=repo.findById(new TradePK("T1",1)).get();
		System.out.println(trade);
	}
	
	
}
