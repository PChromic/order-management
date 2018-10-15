package pchromic.service;

import pchromic.domain.Order;
import org.springframework.stereotype.Service;
import pchromic.domain.Report;

import java.util.List;


public interface ReportService {

    Report setOrderReports(String clientId);

    /**
     * Takes a list of orders as parameter and returns its size.
     * @param orders list of orders
     * @return size of orders list
     */
    Integer getTotalAmountOfOrders(List<Order> orders);
    /**
     * Takes list of all orders as parameter and returns their total value
     * @param orders list of all orders
     * @return total value of orders
     */
    Double getTotalOrdersValue(List<Order> orders);

    /**
     * Takes a list of orders as parameter and returns it.
     * @param orders list of orders
     * @return orders list
     */
    List<Order> getAllOrders(List<Order> orders);

    /**
     * Takes a list of orders as parameter and returns
     * average value of orders belonging to the client
     * @param orders list of orders
     * @return average value of all order items
     */
    Double getAverageValueOfOrder(List<Order> orders);

    /**
     * Takes list of all orders and client id as parameters and
     * return list of orders of the client
     * @param orders list of all orders
     * @param clientId id of client
     * @return list of orders for the client
     */
    List<Order> getAllOrdersForCustomer(List<Order> orders, String clientId);

    /**
     * Takes list of all orders and client id as parameters and
     * returns total value of orders for the client
     * @param orders list of all orders
     * @param clientId id of client
     * @return total value of orders for the client
     */
    Double getTotalOrdersValueForCustomer(List<Order> orders, String clientId);

    /**
     * Takes a list of orders and id of client as parameters and
     * returns total amount of orders belonging to the client
     * @param orders list of all orders
     * @param clientId id of client
     * @return amount of orders for customer
     */
    Long getTotalAmountOfOrdersForCustomer(List<Order> orders, String clientId);

    /**
     * Takes a list of orders and id of client as parameters and
     * returns average value of orders belonging to the client
     * @param orders list of all orders
     * @param clientId id of client
     * @return average value of orders for customer
     */
    Double getAverageValueOfOrderForCustomer(List<Order> orders, String clientId);


}
