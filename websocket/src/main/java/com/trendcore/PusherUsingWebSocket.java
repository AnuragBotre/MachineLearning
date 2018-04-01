package com.trendcore;

import com.neovisionaries.ws.client.*;

import java.io.IOException;

public class PusherUsingWebSocket {

    public static void main(String[] args) throws IOException, WebSocketException {
        createWebSocket();
    }

    private static void createWebSocket() throws IOException, WebSocketException {
        new WebSocketFactory()
                .createSocket("wss://ws.pusherapp.com:443/app/de504dc5763aeef9ff52?client=java-client&protocol=5&version=1.8.0")
                .addListener(new WebSocketAdapter(){
                    @Override
                    public void onTextMessage(WebSocket websocket, String text) throws Exception {
                        super.onTextMessage(websocket, text);
                    }
                })
                .connect();
    }

}
