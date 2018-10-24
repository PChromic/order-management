package pchromic.service;

import pchromic.domain.Report;
import pchromic.enums.ReportType;


public interface ReportService {

    /**
     * Generates reports for list of orders. By default report concerns all orders,
     * when client is specified - only these concerning him
     *
     * @param clientId id of client
     * @return report concerning the list of orders
     */
    Report generateReports(String clientId);
}


