package daisukeclinic.utils;

public class Utility {
    public static int getMaxStringLengthFromArray(String[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() > res) {
                res = arr[i].length();
            }
        }
        return res;
    }

    public static String formatLocalDateTime(java.time.LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy - hh:mm a"));
    }
}
