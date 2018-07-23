package icoRating.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlID;

@XmlEnum
public enum Rating {
	
	@XmlEnumValue("0")
	NOTRATED(0, "Not Rated"),
	@XmlEnumValue("5")
	VERYGOOD(5, "Very Good"),
	@XmlEnumValue("4")
	GOOD(4, "Good"),
	@XmlEnumValue("3")
	SUFFICIENT(3, "Sufficient"),
	@XmlEnumValue("2")
	BAD(2, "Bad"),
	@XmlEnumValue("1")
	VERYBAD(1, "Very Bad");

		
		private Integer code;
		private String text;
	 
	   private Rating(Integer code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	   
	   public Integer getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	  
	   public static Rating getByCode(Integer ratingCode) {
	       for (Rating r : Rating.values()) {
	           if (r.code.equals(ratingCode)) {
	               return r;
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }

	public static Rating parse(String v) {
		// TODO Auto-generated method stub
		return null;
	}
}
