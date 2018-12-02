package pl.dashboard.nbp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        connectAndProcessResponse();
    }

    private static void connectAndProcessResponse() {
        String arg = "2014-07-04";
        String url = "http://api.nbp.pl/api/exchangerates/tables/C/" + arg + "?format=json";

        HttpURLConnection openConnection;
        try {
            openConnection = openHttpURLConnection(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            processConnection(openConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processConnection(HttpURLConnection openConnection) throws IOException {
        InputStream response;
        try {
            response = openConnection.getInputStream();
        } catch (IOException e) {
            System.out.print(openConnection.getResponseMessage());
            return;
        }
        processResponse(response);
    }

    private static HttpURLConnection openHttpURLConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    private static void processResponse(InputStream response) {
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            Gson gson = new Gson();
            ExchangeRatesTable[] exchangeRatesTables = gson.fromJson(responseBody, ExchangeRatesTable[].class);

            List<String> currencyCodes = getCurrencyCodes();

            System.out.print(exchangeRatesTables[0].toString(currencyCodes));
        }
    }

    private static List<String> getCurrencyCodes() {
        return Arrays.asList("USD", "EUR", "CHF", "GBP");
    }
}
