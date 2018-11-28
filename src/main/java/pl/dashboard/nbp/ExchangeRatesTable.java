package pl.dashboard.nbp;

import java.util.Date;

public class ExchangeRatesTable {
    private String table;
    private String no;
    private Date tradingDate;
    private Date effectiveDate;
    private Rate[] rates;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Data: ").append(effectiveDate).append("\n")
                .append("Waluta = kupno; sprzedaż\n");

        for (Rate rate : rates)
        {
            sb.append(rate.toString()).append("\n");
        }

        return sb.toString();
    }
}
