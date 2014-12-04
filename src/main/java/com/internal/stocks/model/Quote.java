package com.internal.stocks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "quotes")
public class Quote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SequenceGenerator(name = "QUOTES_SEQ", allocationSize = 25)
	@Id
	@GeneratedValue(generator = "QUOTES_SEQ")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "symbol_id")
	private Symbol	symbol;
	
	@Column(name = "current_price")
	private float	currentPrice;
	
	@Column(name = "fetch_date")
	private java.util.Date	fetchDate;
	
	private String	fetchTime;
	private float	dayOpen;
	private float	dayHigh;
	private float	dayLow;
	private float	dividend;
	private float	peRatio;
	private float	changePercentage;
	private float	fiftyWeekHigh;
	private float	fiftyWeekLow;
	private Long	volume;
	private float	adjustedClose;
	private float	dayClose;
	private String	exchange;
}
