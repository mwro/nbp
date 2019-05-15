package pl.dashboard.nbp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class ExchangeRatesTable {
    private static final List<String> CURRENCY_CODES_FOR_DISPLAY = List.of("USD", "EUR", "CHF", "GBP");
    private static final String DD_MM_YYYY = "dd.MM.yyyy";

    private String table;
    private String no;
    private Date tradingDate;
    private Date effectiveDate;
    private Rate[] rates;

    @Override
    public String toString() {
        String dateString = getFormattedDate();

        StringBuilder sb = new StringBuilder()
                .append("Data: ").append(dateString).append("\n")
                .append("Waluta = kupno; sprzedaż\n");

        for (Rate rate : rates)
        {
            if (!CURRENCY_CODES_FOR_DISPLAY.contains(rate.getCode())) {
                continue;
            }
            sb.append(rate.toString()).append("\n");
        }

        return sb.toString();
    }

    private String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
        return format.format(effectiveDate);
    }
}
