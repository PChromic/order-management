package pchromic.service;

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
}
