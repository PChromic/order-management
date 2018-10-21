package pchromic.validator;

import pchromic.domain.Order;

import java.util.List;


public interface Validator {

    /**
     * Checks if there are any orders for given client
     * @param orders list of all orders
     * @param clientId id of client
     * @return true if customer has any orders, false if not or id is wrong
     */
    @SuppressWarnings("unused")
    boolean clientExists(List<Order> orders, String clientId);

    /**
     * Validates single line during CSV file parsing
     * @param csvLine array of strings containing splitted values of single row
     * @return true if data format is correct, false otherwise
     */
    @SuppressWarnings("unused")
    boolean isCsvLineValid(String[] csvLine);

    boolean clientHasOrders(List<Order> orders, String clientId);
}
