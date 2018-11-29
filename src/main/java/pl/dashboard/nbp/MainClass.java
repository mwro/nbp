package pl.dashboard.nbp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        try {
            InputStream response = new URL("http://api.nbp.pl/api/exchangerates/tables/C/2013-07-04?format=json").openStream();

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                Gson gson = new Gson();
                ExchangeRatesTable[] exchangeRatesTables = gson.fromJson(responseBody, ExchangeRatesTable[].class);

                List<String> currencyCodes = getCurrencyCodes();

                System.out.print(exchangeRatesTables[0].toString(currencyCodes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getCurrencyCodes() {
        return Arrays.asList("USD", "EUR", "CHF", "GBP");
    }
}
