package pchromic.service.Impl;

import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pchromic.domain.Order;
import pchromic.mapper.CsvOrderMapper;
import pchromic.repository.OrderRepository;
import pchromic.service.OrderService;
import pchromic.validator.Impl.ValidatorImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
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
    public List<String> mapCsv(File file) throws IOException {
        List<String> errorMessages = new ArrayList<>();
        List<String[]> strings = new CSVReader(new FileReader(file)).readAll();


        for (int i = 1; i < strings.size(); i++) {

            boolean isLineValid = validator.isCsvLineValid(strings.get(i));
            if (isLineValid) {
               repository.save(mapper.map(strings.get(i)));
            }
            else {
                errorMessages.add("Wrong line format at line : " + i + "\n");
            }
        }
        return errorMessages;
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
