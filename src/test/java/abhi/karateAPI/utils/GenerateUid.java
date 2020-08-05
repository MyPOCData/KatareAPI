package abhi.karateAPI.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class GenerateUid {

	public String getUidMsisdn(String phoneNumber) {
		final String msisdn = getMsisdnFormatted(phoneNumber);
		final String uuid;
		try {
		      uuid = String.valueOf(hmacSha1Enc("81BHyAUfMgCiu9I7XqArF1Bvy0o", msisdn, 17)) + "0";
		    } catch (Exception e) {
		      e.printStackTrace();
		return null;
		    }
		return uuid;
		  }
		public String getMsisdnFormatted(String phoneNumber) {
		String msisdn = null;
		if (phoneNumber != null && phoneNumber.length() >= 10) {
		      msisdn = (phoneNumber.length() == 10) ? phoneNumber
		: phoneNumber.substring(phoneNumber.length() - 10, phoneNumber.length());
		      msisdn = "+91" + msisdn;
		return msisdn;
		    } else {
		return null;
		    }
		  }
		private String hmacSha1Enc(String key, String in, int max) {
		if (max > 0) {
		final String Base64Data = base64(hmacsha1(key, in));
		final String Base64urlencodeData = Base64Data.replace("+", "-").replace("/", "_")
		          .replace("=", "");
		if (Base64urlencodeData.length() > max) {
		return Base64urlencodeData.substring(0, max);
		      }
		return Base64urlencodeData;
		    }
		return "";
		  }
		private byte[] hmacsha1(String key, String in) {
		try {
		final SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		final Mac mac = Mac.getInstance("HmacSHA1");
		      mac.init(sk);
		return mac.doFinal(in.getBytes());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		return null;
		  }
		private String base64(byte[] hmacsha1_data) {
		final String Base64EncodeData = new String(Base64.encodeBase64(hmacsha1_data));
		return Base64EncodeData;
		  }

}
