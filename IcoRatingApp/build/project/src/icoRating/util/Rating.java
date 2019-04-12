package icoRating.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author Benjamin Wyss
 */
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

	/**
	 * Constructor
	 * @param code
	 * @param text
	 */
	private Rating(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	/**
	 * @return Rating Code as Integer
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return Rating text as String
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param ratingCode
	 * @return enum Rating or null
	 */
	public static Rating getByCode(Integer ratingCode) {
		for (Rating r : Rating.values()) {
			if (r.code.equals(ratingCode)) {
				return r;
			}
		}
		return null;
	}
	
	/**
	 * @return text of Rating
	 */
	@Override
	public String toString() {
		return this.text;
	}
}
