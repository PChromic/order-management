package pchromic.service;

import pchromic.domain.Report;


public interface ReportService {

    /**
     * Generates reports for list of orders. By default report concerns all orders,
     * when client is specified - only these concerning him
     * @param clientId id of client
     * @return report concerning the list of orders
     */
    Report setOrderReports(String clientId);

















}
