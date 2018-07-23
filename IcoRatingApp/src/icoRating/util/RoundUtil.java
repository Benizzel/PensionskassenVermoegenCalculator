package icoRating.util;

import java.math.BigDecimal;

public class RoundUtil {
	
	public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        float rounded = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
        return rounded;
    }
}
