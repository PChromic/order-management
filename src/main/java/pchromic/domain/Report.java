package pchromic.domain;

import pchromic.enums.ReportType;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "report")
@XmlType(
        propOrder = {
                "ordersAmount",
                "ordersValue",
                "ordersAvgValue",
        }
)
public class Report {

    private String ordersAmount;
    private String ordersValue;
    private String ordersAvgValue;
    private ReportType reportType;

    public Report() {
    }

    public Report(String ordersAmount, String ordersValue, String ordersAvgValue, ReportType reportType) {
        this.ordersAmount = ordersAmount;
        this.ordersValue = ordersValue;
        this.ordersAvgValue = ordersAvgValue;
        this.reportType = reportType;
    }

    public String getOrdersAmount() {
        return ordersAmount;
    }

    public void setOrdersAmount(String ordersAmount) {
        this.ordersAmount = ordersAmount;
    }

    public String getOrdersValue() {
        return ordersValue;
    }

    public void setOrdersValue(String ordersValue) {
        this.ordersValue = ordersValue;
    }

    public String getOrdersAvgValue() {
        return ordersAvgValue;
    }

    public void setOrdersAvgValue(String ordersAvgValue) {
        this.ordersAvgValue = ordersAvgValue;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}
