package icoRating.util;

/**
 * @author Benjamin Wyss
 */
public enum Weight {

	HIGH(3, "High"), MEDIUM(2, "Medium"), LOW(1, "Low");

	private Integer code;
	private String text;

	/**
	 * Constructor
	 * @param code
	 * @param text
	 */
	private Weight(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	/**
	 * @return Weight Code as Integer
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return Weight text as String
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param weightCode
	 * @return Weight Code or null
	 */
	public static Weight getByCode(Integer weightCode) {
		for (Weight w : Weight.values()) {
			if (w.code.equals(weightCode)) {
				return w;
			}
		}
		return null;
	}

	/**
	 * @return text of Weight
	 */
	@Override
	public String toString() {
		return this.text;
	}
}
