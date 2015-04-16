package com.saj.recipefinder.enums;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * @author Sajan
 *
 */
public enum Unit {
	
	OF,
	GRAMS,
	ML,
	SLICES;
	
	@JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
	
	@JsonCreator
    public static Unit fromJson(String text) {
        return valueOf(text.toUpperCase());
    }

}
