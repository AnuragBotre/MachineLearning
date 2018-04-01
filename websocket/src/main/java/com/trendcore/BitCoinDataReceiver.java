package com.trendcore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitCoinDataReceiver {

    public static void main(String[] args) throws IOException, WebSocketException {

        ObjectMapper o = new ObjectMapper();

        Map m = new HashMap();
        m.put("event","subscribe");
        m.put("channel","ticker");
        m.put("pair","BTCUSD");


        String s = o.writeValueAsString(m);
        System.out.println(s);


        createWebSocket(s);

        return;
    }

    private static void readJson(ObjectMapper o) throws IOException {
        String out = "[4,10491,36.65841418,10492,67.3007242,-118,-0.0111,10487,39001.15607472,11065,10356]";
        List list = o.readValue(out, List.class);

        list.forEach(o1 -> System.out.println(o1));
    }

    private static void createWebSocket(String s) throws WebSocketException, IOException {
        new WebSocketFactory()
                .createSocket("wss://api.bitfinex.com/ws")
                .addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket ws, String message) throws IOException {
                        // Received a response. Print the received message.
                        //System.out.println(message);

                        ObjectMapper o = new ObjectMapper();
                        List response = o.readValue(message, List.class);

                        if(!"hb".equals(response.get(1))){
                            System.out.println("Asking Price : " + response.get(3));
                            System.out.println("Last Price : " + response.get(7));
                            System.out.println("Bidding Price : " + response.get(1));
                        }

                    }
                }).connect().sendText(s);
    }

}
