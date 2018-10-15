package pchromic.service;

import pchromic.domain.Order;
import org.springframework.stereotype.Service;
import pchromic.domain.Report;

import java.util.List;


public interface ReportService {

    /**
     * Generates reports for list of orders. By default report concerns all orders,
     * when client is specified - only these concerning him
     * @param clientId id of client
     * @return report concerning the list of orders
     */
    Report setOrderReports(String clientId);

    /**
     * Takes a list of orders as parameter and returns its size.
     * @return size of orders list
     */
    Integer getTotalAmountOfOrders();
    /**
     * Takes list of all orders as parameter and returns their total value
     * @return total value of orders
     */
    Double getTotalOrdersValue();

    /**
     * Takes a list of orders as parameter and returns it.
     * @return orders list
     */
    List<Order> getAllOrders();

    /**
     * Takes a list of orders as parameter and returns
     * average value of orders belonging to the client
     * @return average value of all order items
     */
    Double getAverageValueOfOrder();

    /**
     * Takes list of all orders and client id as parameters and
     * return list of orders of the client
     * @param clientId id of client
     * @return list of orders for the client
     */
    List<Order> getAllOrdersForCustomer( String clientId);

    /**
     * Takes list of all orders and client id as parameters and
     * returns total value of orders for the client
     * @param clientId id of client
     * @return total value of orders for the client
     */
    Double getTotalOrdersValueForCustomer( String clientId);

    /**
     * Takes a list of orders and id of client as parameters and
     * returns total amount of orders belonging to the client
     * @param clientId id of client
     * @return amount of orders for customer
     */
    Long getTotalAmountOfOrdersForCustomer( String clientId);

    /**
     * Takes a list of orders and id of client as parameters and
     * returns average value of orders belonging to the client
     * @param clientId id of client
     * @return average value of orders for customer
     */
    Double getAverageValueOfOrderForCustomer( String clientId);


}
