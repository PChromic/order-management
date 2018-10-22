package pchromic.repository.customized;

import pchromic.domain.Order;

import java.util.List;

public interface CustomizedOrderRepository {

    List<Order> getOrdersForClient(String clientId);

    Integer getTotalAmountOfOrders();

    Double getTotalOrdersValue();

    Double getTotalOrdersValueForClient(String clientId);

    List<Order> getAllOrders();

    Integer getOrdersAmountForClient(String clientId);

    Double getAverageValueOfOrder();

    Double getAverageValueOfOrderForClient(String clientId);

}
