package com.saj.recipefinder.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.saj.recipefinder.enums.Unit;
import com.saj.recipefinder.utils.JsonDateDeSerializer;
import com.saj.recipefinder.utils.JsonDateSerializer;
/**
 * @author Sajan
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FridgeItem extends Ingredient{
	
	private Date useBy;
	
	public FridgeItem(){}
	
	public FridgeItem(String item, Double amount, Unit unit, Date useBy) {
        setItem(item);
        setAmount(amount);
        setUnit(unit);
        setUseBy(useBy);
    }

	@JsonSerialize(using=JsonDateSerializer.class)
	@JsonDeserialize(using=JsonDateDeSerializer.class)
    public Date getUseBy() {
        return useBy;
    }

    public void setUseBy(Date useBy) {
        this.useBy = useBy;
    }
    
    @Override
    public String toString() {
        return "FridgeItem{" + super.toString() +
                ", useBy=" + useBy +
                '}';
    }

}
