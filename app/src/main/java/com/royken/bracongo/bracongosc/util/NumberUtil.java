package com.royken.bracongo.bracongosc.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    public static String format(double number){
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.FRENCH);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(number);
    }
}
