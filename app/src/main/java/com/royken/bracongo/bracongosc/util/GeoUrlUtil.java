package com.royken.bracongo.bracongosc.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class GeoUrlUtil {
    public static String wcfROOT = "https://4track.net/";

    //----------------------------------------------------------

    public static String getCamionPositionUrl(String deviceId, String circuit){
        Calendar cal = Calendar.getInstance();
        long ms = cal.getTimeInMillis();
        String token = getToken(ms, deviceId, circuit);
        return  "BRC|"+ deviceId+"|" + circuit + "|" + ms + "|" + token;

    }

    public static String getToken(Long lt, String cdev, String ccode) {
        String cet = Long.toString(lt);

        String pattern = "()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*";
        String un = "";
        int i;
        for (i = 0; i < ccode.length(); i++) {
            un += ccode.charAt(i);
            un += pattern.charAt(i);
        }

        String ret = "";
        un += pattern;
        for (i = 0; i < cdev.length(); i++) {
            ret += cdev.charAt(i);
            ret += un.charAt(i);
        }

        try {
            ret += getSHA256(ccode).toUpperCase();
            ret += cet;
            ret = getSHA256(ret).toUpperCase();
        } catch (Exception e) {

        }
        return ret;
    }

    private static String getSHA256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            byte[] dig = md.digest(str.getBytes("UTF-8"));
            StringBuilder sbld = new StringBuilder(dig.length * 2);
            for (byte b : dig) {
                sbld.append(String.format("%02x", b));
            }
            return sbld.toString();
        } catch (Exception e) {

        }
        return "";
    }
}
