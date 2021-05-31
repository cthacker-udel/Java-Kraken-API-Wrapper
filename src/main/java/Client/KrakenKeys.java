package Client;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;


public class KrakenKeys extends KrakenClient {

    private static final String HMAC_SHA512 = "HmacSHA512";
    private static final String SHA256 = "SHA-256";
    private static final String UTF8 = "UTF-8";
    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";
    private static final String ERROR_NO_PARAMETERS = "The parameters can't be null or empty.";
    private static final String ERROR_NULL_INPUT = "Input can't be null.";
    private static final String ERROR_NULL_ARRAYS = "Given arrays can't be null.";
    private String apiKey;
    private String secretKey;
    private boolean isPublic;
    private static final String ERROR_NULL_METHOD = "The API method can't be null.";
    private static final String PUBLIC_URL = "https://api.kraken.com/0/public/";
    private static final String PRIVATE_URL = "https://api.kraken.com/0/private/";
    HMAC256 mac = new HMAC256();

    public KrakenKeys(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }



    public String generateNonce() {
        return System.currentTimeMillis() + "000";
    }

    public String encodeUTF8(String stringToEncode) throws UnsupportedEncodingException {
        return URLEncoder.encode(stringToEncode, "UTF-8");
    }

    public String encodeParameters(Map<String, Object> params) throws UnsupportedEncodingException {
        //String postData = params.entrySet().stream().map(e -> urlEncodeUTF8(e.getKey()) + "=" + encode(e.getValue()+"", "UTF-8")).reduce((e1, e2) -> e1 + "&" + e2).orElse("");
        ArrayList<String> encodedParams = new ArrayList<>();
        for (Map.Entry<String, Object> eachEntry : params.entrySet()) {
            encodedParams.add(eachEntry.getKey() + "=" + eachEntry.getValue() + "");
        }
        return String.join("&", encodedParams);
    }

    public String generateSignature(Map<String, Object> params) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, MalformedURLException {
        String nonce = generateNonce();
        return nonce;
    }

    public static byte[] hmacSha512(byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA512);
        mac.init(new SecretKeySpec(key, HMAC_SHA512));
        return mac.doFinal(message);
    }

    public static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    public static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] concatArrays(byte[] a, byte[] b) {

        if (a == null || b == null) {
            throw new IllegalArgumentException(ERROR_NULL_ARRAYS);
        }

        byte[] concat = new byte[a.length + b.length];
        for (int i = 0; i < concat.length; i++) {
            concat[i] = i < a.length ? a[i] : b[i - a.length];
        }

        return concat;
    }

    public static byte[] sha256(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA256);
        return md.digest(stringToBytes(message));
    }

    public static byte[] stringToBytes(String input) {

        if (input == null) {
            throw new IllegalArgumentException(ERROR_NULL_INPUT);
        }

        return input.getBytes(Charset.forName(UTF8));
    }

    public static String urlEncode(String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, UTF8);
    }

    public String setParameters(Map<String, Object> parameters) throws UnsupportedEncodingException {

        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_PARAMETERS);
        }

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            postData.append(entry.getKey()).append(EQUAL_SIGN).append(urlEncode(entry.getValue() + "")).append(AMPERSAND);
        }

        return postData.toString();
    }

}
