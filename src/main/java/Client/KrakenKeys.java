package Client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;


public class KrakenKeys extends KrakenClient{

    private String apiKey;
    private String secretKey;
    HMAC256 mac = new HMAC256();

    public KrakenKeys(String apiKey, String secretKey){
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

    public Long generateNonce(){
        return System.currentTimeMillis();
    }

    public String encodeUTF8(String stringToEncode) throws UnsupportedEncodingException {
        return URLEncoder.encode(stringToEncode,"UTF-8");
    }

    public String encodeParameters(Map<String,Object> params) throws UnsupportedEncodingException {
        //String postData = params.entrySet().stream().map(e -> urlEncodeUTF8(e.getKey()) + "=" + encode(e.getValue()+"", "UTF-8")).reduce((e1, e2) -> e1 + "&" + e2).orElse("");
        ArrayList<String> encodedParams = new ArrayList<>();
        for(Map.Entry<String,Object> eachEntry : params.entrySet()){
            encodedParams.add(encodeUTF8(eachEntry.getKey()) + "=" + encodeUTF8(eachEntry.getValue() + ""));
        }
        return String.join("&",encodedParams);
    }

    public String generateSignature(String urlPath, Map<String,Object> params) throws UnsupportedEncodingException{
        String encoded = encodeUTF8(params.get("nonce") + "" + encodeParameters(params));
        String message = urlPath + mac.hmacDigest(encoded,secretKey,"HmacSHA256");
        byte[] base64DecodedSecretKey = Base64.getDecoder().decode(secretKey);
        byte[] result = Base64.getEncoder().encode(mac.hmacDigest(message,new String(base64DecodedSecretKey, StandardCharsets.UTF_8),"HmacSHA512").getBytes());
        return new String(Base64.getDecoder().decode(result),StandardCharsets.UTF_8);
    }


}
