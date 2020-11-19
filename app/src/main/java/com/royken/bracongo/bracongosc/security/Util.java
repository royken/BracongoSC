package com.royken.bracongo.bracongosc.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    public static String getToken(long ms, String dev, String circ) {
        String ret = "";
        try {
            String pwh = bin2hex(getHash(circ));
            String pattern = "()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*()<>:?/.,{}[]-=+_~!@#$%^&*";
            String uN = "";
            for (int i = 0; i < circ.length(); i++) {
                uN += circ.charAt(i);
                uN += pattern.charAt(i);
            }
            uN += pattern; //ensure length of uN is > length of dev
            for (int i = 0; i < dev.length(); i++) {
                ret += dev.charAt(i);
                ret += uN.charAt(i);
            }
            ret += pwh;
            ret += ms;
            ret = bin2hex(getHash(ret));
        } catch (Exception e) {
            class Local {
            };
            ret = "error";
        }
        return ret;
    }

    //----------------------------------------------------------
    public static byte[] getHash(String password) {
        MessageDigest digest = null;
        byte[] pbyte = new byte[2];
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
        }
        digest.reset();
        try {
            pbyte = password.getBytes("UTF-16LE");
            int len = password.length();
            if (pbyte.length == 2 * len) {//insert -1 and -2?
                byte[] ptemp = new byte[pbyte.length + 2];
                ptemp[0] = -1;
                ptemp[1] = -2;
                System.arraycopy(pbyte, 0, ptemp, 2, pbyte.length);
                pbyte = ptemp;
            }

            byte[] pb = new byte[pbyte.length - 2];
            for (int x = 0; x < pb.length; x++) {
                pb[x] = pbyte[x + 2];
            }
            byte[] hash = digest.digest(pb);
            return hash;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    //----------------------------------------------------------
    public static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }
}
