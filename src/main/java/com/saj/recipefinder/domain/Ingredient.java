package com.saj.recipefinder.domain;

import com.saj.recipefinder.enums.Unit;

/**
 * @author Sajan
 *
 */
public class Ingredient {
	
	private Long id; 
	private String item;
	private Double amount;
	private Unit unit;
	
	public Ingredient(){}
	
	public Ingredient(Long id, String item, Double amount, Unit unit) {
		super();
		this.id = id;
		this.item = item;
		this.amount = amount;
		this.unit = unit;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Override
    public String toString() {
        return "Ingredient{" +
        		"id=" + id + 
                ", item='" + item + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }

}
