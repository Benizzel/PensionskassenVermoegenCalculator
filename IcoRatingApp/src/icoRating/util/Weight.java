package icoRating.util;

import javafx.beans.property.IntegerProperty;

public enum Weight {
	
	   HIGH(3, "High"), MEDIUM(2, "Medium"), LOW(1, "Low");
	 
	   private Integer code;
	   private String text;
	 
	   private Weight(Integer code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	   
	   public Integer getCode () {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static Weight getByCode(Integer weightCode) {
	       for (Weight w : Weight.values()) {
	           if (w.code.equals(weightCode)) {
	               return w;
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }

}
