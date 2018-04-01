package com.trendcore.linear_regression;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleLinearModel {

    public static final String HISTORICAL_DATA_ID = "historical-data";

    public static void main(String[] args) {
        //bitcoin_market_info = pd.read_html("https://coinmarketcap.com/currencies/bitcoin/historical-data/?start=20130428&end="+time.strftime("%Y%m%d"))[0]

        //read data from the url
        try {
            Date d = new Date(System.currentTimeMillis());

            SimpleDateFormat dateFormat = new SimpleDateFormat("YMMd");

            System.out.println(dateFormat.format(d));


            Document doc = Jsoup.connect("https://coinmarketcap.com/currencies/bitcoin/historical-data/?start=20130428&end="+dateFormat.format(d)).get();

            doc.getElementById(HISTORICAL_DATA_ID)
                .getElementsByTag("table")
                .first()
                .getElementsByTag("tbody")
                .first()
                .getElementsByTag("tr")
                .stream()
                .map(tr -> {

                    Map map = new HashMap<>();
                    List<String> values = tr.getElementsByTag("td").stream().map(td -> {
                        return td.html();
                    }).collect(Collectors.toList());
                    map.put("date",values.get(0));
                    map.put("open",values.get(1));
                    map.put("high",values.get(2));
                    map.put("low",values.get(3));
                    map.put("close",values.get(4));
                    map.put("volume",values.get(5));
                    map.put("marketCapacity",values.get(6));
                    return map;
                }).forEach(map -> {
                    map.forEach((o, o2) -> System.out.print(o2 + " "));
                    System.out.println();
                });
                /*.forEach(element -> {
                    System.out.println(element.nodeName());
                })*/;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
