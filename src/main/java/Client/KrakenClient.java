package Client;

public class KrakenClient {

    KrakenKeys apiKeys;

    public KrakenClient(){

    }

    public KrakenClient(String apiKey, String secretKey){
        this.apiKeys = new KrakenKeys(apiKey,secretKey);
    }

}
