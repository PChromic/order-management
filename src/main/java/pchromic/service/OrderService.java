package pchromic.service;

import javafx.collections.ObservableList;
import pchromic.domain.Order;
import pchromic.exception.WrongOrderFormatException;

import java.io.BufferedReader;
import java.io.IOException;

public interface OrderService {

    void addOrder(Order order);

    /**
     * Maps orders from CSV type file to Order list
     * @param bufferedReader file input
     * @throws IOException throws exception when file cannot be red
     */
    void mapCsv (BufferedReader bufferedReader) throws IOException, WrongOrderFormatException;

    /**
     * Takes client id as parameter and fills table with full list of orders
     * if client as default or list of the client if its id is specified
     * @param clientId id of client
     */
    ObservableList<Order> setOrderTableContent(String clientId);
}
