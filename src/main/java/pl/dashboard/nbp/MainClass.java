package pl.dashboard.nbp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        connectAndProcessResponse();
    }

    private static void connectAndProcessResponse() {
        String arg = "1991-07-04";

        if (!isValidArgument(arg))
        {
            return;
        }

        String url = "http://api.nbp.pl/api/exchangerates/tables/C/" + arg + "?format=json";

        HttpURLConnection openConnection;
        try {
            openConnection = openHttpURLConnection(url);
            processConnection(openConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidArgument(String arg) {
        if (arg.equals("")) {
            return true;
        }

        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate ld;
        try {
            ld = LocalDate.parse(arg, fomatter);
        } catch (DateTimeParseException e)
        {
            System.out.print(e.getMessage() + "\n" + "The proper argument is date in format \"yyyy-MM-dd\" or empty string.");
            return false;
        }

        String result = ld.format(fomatter);
        return result.equals(arg);
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
        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        Gson gson = new Gson();
        ExchangeRatesTable[] exchangeRatesTables = gson.fromJson(responseBody, ExchangeRatesTable[].class);

        List<String> currencyCodes = getCurrencyCodes();

        System.out.print(exchangeRatesTables[0].toString(currencyCodes));
    }

    private static List<String> getCurrencyCodes() {
        return Arrays.asList("USD", "EUR", "CHF", "GBP");
    }
}
