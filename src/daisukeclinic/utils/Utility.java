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
}
