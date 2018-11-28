package pl.dashboard.nbp;

import java.math.BigDecimal;

public class Rate {
    private String currency;
    private String code;
    private BigDecimal bid;
    private BigDecimal ask;

    @Override
    public String toString() {
        return code + " = " + bid + "; " + ask;
    }
}
