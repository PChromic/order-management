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
        List<Order> orders = repository.findAll();
        if (clientId.isEmpty()){
            orderAmount = this.getTotalAmountOfOrders().toString();
            ordersValue = this.getTotalOrdersValue().toString();
            ordersAvgValue = this.getAverageValueOfOrder().toString();

        }
        else {
            orderAmount = this.getTotalAmountOfOrdersForCustomer( clientId).toString();
            ordersValue = this.getTotalOrdersValueForCustomer( clientId).toString();
            ordersAvgValue = this.getAverageValueOfOrderForCustomer( clientId).toString();
        }
        return  new Report(orderAmount,ordersValue,ordersAvgValue);
    }

    @Override
    public Integer getTotalAmountOfOrders() {

        return repository.findAll().size();
    }

    @Override
    public Long getTotalAmountOfOrdersForCustomer(String clientId) {
        return repository.findAll().stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .count();
    }

    @Override
    public Double getTotalOrdersValue() {

        double sum = repository.findAll().stream()
                .mapToDouble(Order::getPrice)
                .sum();

        return BigDecimal
                .valueOf(sum)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getTotalOrdersValueForCustomer(String clientId) {

        double sum = repository.findAll().stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .mapToDouble(Order::getPrice)
                .sum();


        return BigDecimal
                .valueOf(sum)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public List<Order> getAllOrdersForCustomer( String clientId) {

        return repository.findAll().stream().
                filter(id -> clientId.equals(id.getClientId()))
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageValueOfOrder() {
        double sum = repository.findAll().stream()
                .mapToDouble(Order::getPrice)
                .sum();


        return BigDecimal
                .valueOf(sum/repository.findAll().size())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getAverageValueOfOrderForCustomer(String clientId) {

        double sum = repository.findAll().stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .mapToDouble(Order::getPrice)
                .sum();

        double customersOrderCount = repository.findAll().stream()
                .filter(id -> clientId.equals(id.getClientId()))
                .count();

        return BigDecimal
                .valueOf(sum/customersOrderCount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
