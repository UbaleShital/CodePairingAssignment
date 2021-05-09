package com.trade.store.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PostLoad;

import com.trade.store.repository.TradeRepository;
import com.trade.store.service.TradeService;

@Entity
public class Trade{
	
	@EmbeddedId
	private TradePK tradePk;
	
	@Column(name="counter_party_id")
	private String counterPartyId;
	
	@Column(name="book_id")
	private String bookId;
	
	@Column(name="maturity_date")
	private Date maturityDate;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="expired")
	private String expired;
	
	
	public Trade() {}
	
	public Trade(TradePK tradePk, String counterPartyId, String bookId, Date maturityDate, Date createdDate,
			String expired) {
		super();
		this.tradePk = tradePk;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.expired = expired;
	}

	public TradePK getTradePk() {
		return tradePk;
	}
	public void setTradePk(TradePK tradePk) {
		this.tradePk = tradePk;
	}
	public String getCounterPartyId() {
		return counterPartyId;
	}
	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
	@Override
	public String toString() {
		return "Trades [tradePk=" + tradePk + ", counterPartyId=" + counterPartyId + ", bookId=" + bookId
				+ ", maturityDate=" + maturityDate + ", createdDate=" + createdDate + ", expired=" + expired + "]";
	}
	
	
	
	
}
