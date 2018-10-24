package pchromic.service;

import javafx.collections.ObservableList;
import pchromic.domain.Order;



import java.io.File;
import java.io.IOException;
import java.util.List;

public interface OrderService {

    /**
     * Persists order to database
     * @param order - order to be persisted
     */
    void addOrder(Order order);

    /**
     * Returns number of orders persisted to database
     * @return number of orders
     */
    Long getTotalAmountOfOrders();

    /**
     * Returns amount of orders belonging to the client
     * @param clientId id of client
     * @return amount of orders for customer
     */
    Integer getTotalAmountOfOrdersForClient(String clientId);

    /**
     * Returns total value of orders in database
     * @return total value of orders
     */
    Double getTotalOrdersValue();

    /**
     * Returns total value of orders for the client
     * @param clientId id of client
     * @return total value of orders for the client
     */
    Double getTotalOrdersValueForClient(String clientId);

    /**
     * Returns list of all orders persisted to database
     * @return orders list
     */
    List<Order> getAllOrders();

    /**
     * Returns list of orders of the client
     * @param clientId id of client
     * @return list of orders for the client
     */
    List<Order> getAllOrdersForClient(String clientId);

    /**
     * Returns average value of orders belonging to the client
     * @return average value of all order items
     */
    Double getAverageValueOfOrder();

    /**
     * Returns average value of orders belonging to the client
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
