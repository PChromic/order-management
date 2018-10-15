package pchromic.repository.customized;

import pchromic.domain.Order;

import java.util.List;

public interface CustomizedOrderRepository {

    List<Order> getOrdersByClientId(String clientId);

}
