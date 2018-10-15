package pchromic.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pchromic.domain.Order;
import pchromic.domain.Report;
import pchromic.repository.OrderRepository;
import pchromic.service.ReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderRepository repository;

    @Override
    public Report setOrderReports(String clientId){
        String orderAmount;
        String ordersValue;
        String ordersAvgValue;
        List<Order> orders2 = repository.findAll();
        if (clientId.isEmpty()){
            orderAmount = this.getTotalAmountOfOrders(orders2).toString();
         //   this.ordersAmount.setText(orderAmount);
            ordersValue = this.getTotalOrdersValue(orders2).toString();
         //   this.ordersValue.setText(ordersValue);
            ordersAvgValue = this.getAverageValueOfOrder(orders2).toString();
         //   this.ordersAvgValue.setText(ordersAvgValue);

        }
        else {
            orderAmount = this.getTotalAmountOfOrdersForCustomer(orders2, clientId).toString();
         //   this.ordersAmount.setText(orderAmount);
            ordersValue = this.getTotalOrdersValueForCustomer(orders2, clientId).toString();
         //   this.ordersValue.setText(ordersValue);
            ordersAvgValue = this.getAverageValueOfOrderForCustomer(orders2, clientId).toString();
         //   this.ordersAvgValue.setText(ordersAvgValue);

        }
        return  new Report(orderAmount,ordersValue,ordersAvgValue);
    }

    @Override
    public Integer getTotalAmountOfOrders(List<Order> orders) {

        return orders.size();
    }

    @Override
    public Long getTotalAmountOfOrdersForCustomer(List<Order> orders, String clientId) {
        return orders.stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .count();
    }

    @Override
    public Double getTotalOrdersValue(List<Order> orders) {

        double sum = orders.stream()
                .mapToDouble(Order::getPrice)
                .sum();

        return BigDecimal
                .valueOf(sum)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getTotalOrdersValueForCustomer(List<Order> orders, String clientId) {

        double sum = orders.stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .mapToDouble(Order::getPrice)
                .sum();


        return BigDecimal
                .valueOf(sum)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public List<Order> getAllOrders(List<Order> orders) {
        return orders;
    }

    @Override
    public List<Order> getAllOrdersForCustomer(List<Order> orders, String clientId) {

        return orders.stream().
                filter(id -> clientId.equals(id.getClientId()))
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageValueOfOrder(List<Order> orders) {
        double sum = orders.stream()
                .mapToDouble(Order::getPrice)
                .sum();


        return BigDecimal
                .valueOf(sum/orders.size())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getAverageValueOfOrderForCustomer(List<Order> orders, String clientId) {

        double sum = orders.stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .mapToDouble(Order::getPrice)
                .sum();

        double customersOrderCount = orders.stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .count();

        return BigDecimal
                .valueOf(sum/customersOrderCount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
