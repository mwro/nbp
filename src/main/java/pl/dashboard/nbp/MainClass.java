package pl.dashboard.nbp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class MainClass {

    private static final String NBP_EXCHANGERATES_URL = "http://api.nbp.pl/api/exchangerates/tables/C/";
    private static final String FORMAT_JSON_URL_PARAMETER = "?format=json";
    private static final String STRICT_YMD_DATE_FORMAT = "uuuu-MM-dd";
    private static final String DISPLAY_YMD_FORMAT = "yyyy-MM-dd";

    public static void main(String[] args) {
        String arg = "";
        if (args.length > 0) {
            arg = args[0];
        }
        connectAndProcessResponse(arg);
    }

    private static void connectAndProcessResponse(String arg) {
        if (!isValidArgument(arg)) {
            return;
        }

        HttpURLConnection connection;
        try {
            connection = openHttpURLConnection(getUrl(arg));
        } catch (IOException e) {
            System.out.println("Error opening connection: " + e.getMessage());
            return;
        }

        printResponseFromConnection(connection);
    }

    private static String getUrl(String arg) {
        return NBP_EXCHANGERATES_URL + arg + FORMAT_JSON_URL_PARAMETER;
    }

    private static boolean isValidArgument(String arg) {
        if (arg.equals("")) {
            return true;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STRICT_YMD_DATE_FORMAT)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(arg, formatter);
        } catch (DateTimeParseException e) {
            System.out.print(e.getMessage() + "\n" + "The proper argument is date in format \"" + DISPLAY_YMD_FORMAT + "\" or empty string.");
            return false;
        }
        return true;
    }

    private static HttpURLConnection openHttpURLConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    private static void printResponseFromConnection(HttpURLConnection connection) {
        try (final InputStream response = connection.getInputStream();
             final Scanner scanner = new Scanner(response)) {

            String responseBody = scanner.useDelimiter("\\A").next();
            ExchangeRatesTable[] exchangeRatesTables = getExchangeRatesTablesFromResponse(responseBody);

            System.out.print(exchangeRatesTables[0].toString());
        } catch (IOException e) {
            printResponseMessage(connection);
        }
    }

    private static ExchangeRatesTable[] getExchangeRatesTablesFromResponse(String responseBody) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateJsonDeserializer())
                .create();
        return gson.fromJson(responseBody, ExchangeRatesTable[].class);
    }

    private static void printResponseMessage(HttpURLConnection connection) {
        try {
            System.out.println(connection.getResponseMessage());
        } catch (IOException e) {
            System.out.println("Error occurred connecting to the server: " + e.getMessage());
        }
    }
}
