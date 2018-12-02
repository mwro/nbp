package pl.dashboard.nbp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class ExchangeRatesTable {
    private String table;
    private String no;
    private Date tradingDate;
    private Date effectiveDate;
    private Rate[] rates;

    String toString(List<String> currencyCodes) {
        String dateString = getFormattedDate();

        StringBuilder sb = new StringBuilder()
                .append("Data: ").append(dateString).append("\n")
                .append("Waluta = kupno; sprzedaż\n");

        for (Rate rate : rates)
        {
            if (!currencyCodes.contains(rate.getCode())) {
                continue;
            }
            sb.append(rate.toString()).append("\n");
        }

        return sb.toString();
    }

    private String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(effectiveDate);
    }
}
