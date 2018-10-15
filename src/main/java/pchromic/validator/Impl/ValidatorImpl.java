package pchromic.validator.Impl;

import org.springframework.stereotype.Component;
import pchromic.domain.Order;
import pchromic.validator.Validator;

import java.util.List;
@Component
public class ValidatorImpl implements Validator {

    @Override
    public boolean customerExists(List<Order> orders, String clientId) {

        long count = orders.stream()
                .map(Order::getClientId)
                .filter(id -> id.equals(clientId))
                .count();

        return count > 0;
    }

    @Override
    public boolean isCsvLineValid(String[] csvLine) {
        return csvLine.length == 5;
    }

    @Override
    public boolean customerHasOrders(List<Order> orders, String clientId) {
        boolean isNumericOrEmpty = clientId.matches("^[0-9]+$") || clientId.isEmpty();
        return isNumericOrEmpty && (this.customerExists(orders, clientId) || clientId.isEmpty());
    }
}
