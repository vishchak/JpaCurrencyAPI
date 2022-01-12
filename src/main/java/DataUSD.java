import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DataUSD {
    public static Currency usdRateThatDay(LocalDate date) throws IOException {

        String fancyDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String json = "";
        String data;
        Gson gson = new GsonBuilder().create();
        URL url = new URL("https://api.privatbank.ua/p24api/exchange_rates?json&date=" + fancyDate);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
            while ((data = br.readLine()) != null) {
                json = data;
            }
        }
        String[] currencies = json.split("baseCurrency\"");
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].contains("USD")) {
                json = "{baseCurrency" + currencies[i].substring(0, currencies[i].length() - 3);

            }
        }
        Currency cur = gson.fromJson(json, Currency.class);
        cur.setDate(date);
        System.out.println(cur);
        return cur;
    }
}
