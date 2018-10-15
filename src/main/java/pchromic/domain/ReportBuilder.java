package pchromic.domain;

public final class ReportBuilder {
    private String ordersAmount;
    private String ordersValue;
    private String ordersAvgValue;

    private ReportBuilder() {
    }

    public static ReportBuilder aReport() {
        return new ReportBuilder();
    }

    public ReportBuilder withOrdersAmount(String ordersAmount) {
        this.ordersAmount = ordersAmount;
        return this;
    }

    public ReportBuilder withOrdersValue(String ordersValue) {
        this.ordersValue = ordersValue;
        return this;
    }

    public ReportBuilder withOrdersAvgValue(String ordersAvgValue) {
        this.ordersAvgValue = ordersAvgValue;
        return this;
    }

    public Report build() {
        Report report = new Report();
        report.setOrdersAmount(ordersAmount);
        report.setOrdersValue(ordersValue);
        report.setOrdersAvgValue(ordersAvgValue);
        return report;
    }
}
