package com.trade.store.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.trade.store.model.TradePK;
import com.trade.store.helper.ConstantsVar;
import com.trade.store.model.Trade;

public interface TradeRepository extends CrudRepository<Trade, TradePK> {
	@Query(value=ConstantsVar.SELECT_PK)
	public List<TradePK> findByVersion(); 
	
	@Transactional
	@Modifying
	@Query(value=ConstantsVar.UPDATE_EXPIRED_FLAG,nativeQuery=true)
	public int autoUpdateFlag(@Param("currDate") Date date);
}
