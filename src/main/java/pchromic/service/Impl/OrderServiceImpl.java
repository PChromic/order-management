package pchromic.service.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pchromic.domain.Order;
import pchromic.exception.WrongOrderFormatException;
import pchromic.mapper.CsvOrderMapper;
import pchromic.repository.OrderRepository;
import pchromic.service.OrderService;
import pchromic.validator.Impl.ValidatorImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private
    ValidatorImpl validator;

    @Autowired
    private
    OrderRepository repository;

    @Autowired
    private
    CsvOrderMapper mapper;

    @Override
    public void addOrder(Order order) {
        repository.save(order);
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
    public List<Order> getAllOrdersForCustomer(String clientId) {

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

    @Override
    public void mapCsv(BufferedReader bufferedReader) throws IOException, WrongOrderFormatException {

        bufferedReader.readLine();
        String line = bufferedReader.readLine();

        while (!Objects.isNull(line)) {
            String[] csvOrder = line.split(",");
            boolean isLineValid = validator.isCsvLineValid(csvOrder);
            if (isLineValid) {
                repository.save(mapper.map(csvOrder));
                line = bufferedReader.readLine();
            }
            else {
                line = bufferedReader.readLine();
                throw new WrongOrderFormatException();
            }
        }
    }

    @Override
    public ObservableList<Order> setOrderTableContent(String clientId) {
        if (clientId.isEmpty()){

            return FXCollections.observableArrayList(repository.findAll());
        }
        else{

            return FXCollections.observableArrayList(repository.getOrdersByClientId(clientId));

        }
    }
}
