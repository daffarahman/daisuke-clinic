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

    public static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // Convert bytes to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

}
