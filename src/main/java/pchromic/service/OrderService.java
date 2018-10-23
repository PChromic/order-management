package pchromic.service;

import javafx.collections.ObservableList;
import pchromic.domain.Order;



import java.io.File;
import java.io.IOException;
import java.util.List;

public interface OrderService {

    void addOrder(Order order);

    /**
     * Takes a list of orders as parameter and returns its size.
     * @return size of orders list
     */
    Long getTotalAmountOfOrders();

    /**
     * Takes a list of orders and id of client as parameters and
     * returns total amount of orders belonging to the client
     * @param clientId id of client
     * @return amount of orders for customer
     */
    Integer getTotalAmountOfOrdersForClient(String clientId);

    /**
     * Takes list of all orders as parameter and returns their total value
     * @return total value of orders
     */
    Double getTotalOrdersValue();

    /**
     * Takes list of all orders and client id as parameters and
     * returns total value of orders for the client
     * @param clientId id of client
     * @return total value of orders for the client
     */
    Double getTotalOrdersValueForClient(String clientId);

    /**
     * Takes a list of orders as parameter and returns it.
     * @return orders list
     */
    List<Order> getAllOrders();

    /**
     * Takes list of all orders and client id as parameters and
     * return list of orders of the client
     * @param clientId id of client
     * @return list of orders for the client
     */
    List<Order> getAllOrdersForClient(String clientId);

    /**
     * Takes a list of orders as parameter and returns
     * average value of orders belonging to the client
     * @return average value of all order items
     */
    Double getAverageValueOfOrder();

    /**
     * Takes a list of orders and id of client as parameters and
     * returns average value of orders belonging to the client
     * @param clientId id of client
     * @return average value of orders for customer
     */
    Double getAverageValueOfOrderForClient(String clientId);

    /**
     * Maps orders from CSV type file to Order list
     * @throws IOException throws exception when file cannot be red
     */
    List<String> mapCsv (File file ) throws IOException;

    /**
     * Takes client id as parameter and fills table with full list of orders
     * if client as default or list of the client if its id is specified
     * @param clientId id of client
     */
    ObservableList<Order> setOrderTableContent(String clientId);
}
