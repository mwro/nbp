package pl.dashboard.nbp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

class ExchangeRatesTable {
    private static final List<String> CURRENCY_CODES_FOR_DISPLAY = List.of("USD", "EUR", "CHF", "GBP");
    private static final String EFFECTIVE_DATE_DISPLAY_FORMAT = "dd.MM.yyyy";

    private String table;
    private String no;
    private LocalDate tradingDate;
    private LocalDate effectiveDate;
    private Rate[] rates;

    @Override
    public String toString() {
        String dateString = getStringFromEffectiveDate();

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

    private String getStringFromEffectiveDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(EFFECTIVE_DATE_DISPLAY_FORMAT)
                .withResolverStyle(ResolverStyle.STRICT);

        return formatter.format(effectiveDate);
    }
}
