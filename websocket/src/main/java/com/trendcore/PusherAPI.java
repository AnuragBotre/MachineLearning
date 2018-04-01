package com.trendcore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PusherAPI {

    public static void main(String[] args) {


        /**
         * API Key is related to Bitstamp
         *
         * Need live_trades
         *
         */
        Pusher pusher = new Pusher("de504dc5763aeef9ff52");

        Channel channel = pusher.subscribe("order_book");

        //Bind to listen for events called "my-event" sent to "my-channel"
        channel.bind("data", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channel, String event, String data) {
                System.out.println("Received event with data: " + data);
            }
        });

        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                System.out.println("State changed to " + change.getCurrentState() +
                        " from " + change.getPreviousState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                System.out.println("There was a problem connecting!");
            }
        }, ConnectionState.ALL);

        pusher.connect();


        Scanner s = new Scanner(System.in);
        String next = s.next();

    }



}
