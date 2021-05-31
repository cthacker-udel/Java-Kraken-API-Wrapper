package Client;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class KrakenClient {

    KrakenKeys apiKeys;

    public KrakenClient(){

    }

    public KrakenClient(String apiKey, String secretKey){
        this.apiKeys = new KrakenKeys(apiKey,secretKey);
    }

    public KrakenKeys getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(KrakenKeys apiKeys) {
        this.apiKeys = apiKeys;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, MalformedURLException {

        KrakenClient client = new KrakenClient("","kQH5HW/8p1uGOVjbgWA7FunAmGO8lsSUXNsu3eow76sz84Q18fWxnyRzBHCd3pd5nE9qa99HAZtuZuj6F1huXg==");
        Long nonce = 1616492376594L;
        Map<String,Object> payload = new LinkedHashMap<>();
        payload.put("nonce",nonce);
        payload.put("ordertype","limit");
        payload.put("pair","XBTUSD");
        payload.put("price",37500);
        payload.put("type","buy");
        payload.put("volume",1.25);
        System.out.println(client.getApiKeys().generateSignature(payload));

    }

}
