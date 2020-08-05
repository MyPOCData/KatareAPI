package abhi.karateAPI.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SignatureException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TvUtknGenerator {
	
	GenerateUid generateUid = new GenerateUid();
	
	public String getSecret() {
	    String secret = null;
	    String env = "PROD";
	    if (env.equalsIgnoreCase("PROD")) {
	      secret = "2d7db9";
	    } else if (env.equalsIgnoreCase("DEV")) {
	      secret = "blabla";
	    }
	    return secret;
	  }
	
	public String getUtkn(String phoneNumber, String method, String requestUri) {
	    String token = "";
	    String payload = "";
	    String uid = generateUid.getUidMsisdn(phoneNumber);
	    if (method.trim().equalsIgnoreCase("GET")) {
	      token = generateSignatureGET(requestUri, uid);
	    } else if (method.trim().equalsIgnoreCase("POST")) {
	      token = generateSignaturePOST(requestUri, payload, uid);
	    }
	    
	    return token;
	  }

//	  public  String getUtkn(String phoneNumber, String method, String requestUri,
//	      String payload) {
//	    String token = "";
//	    String uid = GenerateUid.getUidMsisdn(phoneNumber);
//	    if (method.trim().equalsIgnoreCase("GET")) {
//	      token = generateSignatureGET(requestUri, uid);
//	    } else if (method.trim().equalsIgnoreCase("POST")) {
//	      token = generateSignaturePOST(requestUri, payload, uid);
//	    }
//	    
//	    return token;
//	  }

	  public  String generateSignatureGET(String url1, String uid) {
	    String token;
	    String signature = null;
	    String digestStr = null;
	    try {
	      token = calculateRFC2104HMAC(uid, getSecret());
	      URL uri = new URL(URLDecoder.decode(url1, "UTF-8"));
	      if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
	        digestStr = uri.getPath() + "?" + uri.getQuery();
	      } else {
	        digestStr = uri.getPath();
	      }
	      signature = generateHMACSignature("GET", digestStr, token);
	    } catch (SignatureException e) {
	      e.getMessage();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    return getSignatureInFormat(uid, signature);
	  }

	  public  String generateSignaturePOST(String url1, String payload, String uid) {
	    String signature = null;
	    String digestStr = null;
	    try {
	      String token = calculateRFC2104HMAC(uid, getSecret());
	      URL uri = new URL(URLDecoder.decode(url1, "UTF-8"));
	      if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
	        digestStr = uri.getPath() + "?" + uri.getQuery();
	      } else {
	        digestStr = uri.getPath();
	      }
	      if (StringUtils.isEmpty(payload)) {
	        signature = generateHMACSignature("POST", digestStr, token);
	      } else {
	        signature = generateHMACSignature("POST", digestStr, payload,
	            token);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return getSignatureInFormat(uid, signature);
	  }

	  public  String generateHMACSignature(String httpVerb, String requestUri, String secret)
	      throws SignatureException {
	    String signature = StringUtils.EMPTY;
	    String digestString = new StringBuilder(httpVerb).append(requestUri).toString();
	    try {
	      signature = calculateRFC2104HMAC(digestString, secret);
	    } catch (SignatureException e) {
	      e.getMessage();
	      throw e;
	    }
	    return signature;
	  }

	  public  String generateHMACSignature(String httpVerb, String requestUri, String payload,
	      String secret) throws SignatureException {
	    String signature = StringUtils.EMPTY;
	    String digestString = new StringBuilder(httpVerb).append(requestUri).append(payload).toString();
	    try {
	      signature = calculateRFC2104HMAC(digestString, secret);
	    } catch (SignatureException e) {
	      e.getMessage();
	      throw e;
	    }
	    return signature;
	  }

	  public  String getSignatureInFormat(String uid, String signature) {
	    return uid + ":" + signature;
	  }

	  public  String calculateRFC2104HMAC(String data, String secretKey)
	      throws SignatureException {
	    String result;
	    try {
	      Mac mac = Mac.getInstance("HmacSHA1");
	      SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
	      mac.init(key);
	      byte[] authentication = mac.doFinal(data.getBytes());
	      result = new String(org.apache.commons.codec.binary.Base64.encodeBase64(authentication));
	    } catch (Exception e) {
	      e.getMessage();
	      throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
	    }
	    return result;
	  }
	
	

}
