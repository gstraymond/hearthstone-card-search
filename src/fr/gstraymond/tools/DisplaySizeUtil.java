package fr.gstraymond.tools;

import java.text.DecimalFormat;

public class DisplaySizeUtil {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.#");
    private static final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };

	public static String getFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return DECIMAL_FORMAT.format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
