package pchromic.repository.customized;

import pchromic.domain.Order;

import java.util.List;

/**
 * Interface of methods responsible for creating queries and obtaining results
 */
public interface CustomizedOrderRepository {

    List<Order> getOrdersForClient(String clientId);

    Long getTotalAmountOfOrders();

    Double getTotalOrdersValue();

    Double getTotalOrdersValueForClient(String clientId);

    List<Order> getAllOrders();

    Integer getOrdersAmountForClient(String clientId);

    Double getAverageValueOfOrder();

    Double getAverageValueOfOrderForClient(String clientId);

}
