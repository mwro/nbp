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

        String url = NBP_EXCHANGERATES_URL + arg + FORMAT_JSON_URL_PARAMETER;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STRICT_YMD_DATE_FORMAT)
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate ld;
        try {
            ld = LocalDate.parse(arg, formatter);
        } catch (DateTimeParseException e) {
            System.out.print(e.getMessage() + "\n" + "The proper argument is date in format \"" + DISPLAY_YMD_FORMAT + "\" or empty string.");
            return false;
        }

        String result = ld.format(formatter);
        return result.equals(arg);
    }

    private static HttpURLConnection openHttpURLConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
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

    private static void processResponse(InputStream response) {
        Scanner scanner = new Scanner(response);
        String responseBody = scanner.useDelimiter("\\A").next();
        Gson gson = new Gson();
        ExchangeRatesTable[] exchangeRatesTables = gson.fromJson(responseBody, ExchangeRatesTable[].class);

        System.out.print(exchangeRatesTables[0].toString());
    }
}
