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

        return repository.getTotalAmountOfOrders();
    }

    @Override
    public Integer getTotalAmountOfOrdersForClient(String clientId) {
        return repository.getOrdersAmountForClient(clientId);
    }

    @Override
    public Double getTotalOrdersValue() {

        return repository.getTotalOrdersValue();
    }

    @Override
    public Double getTotalOrdersValueForClient(String clientId) {

        return repository.getTotalOrdersValueForClient(clientId);
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public List<Order> getAllOrdersForClient(String clientId) {

        return repository.getOrdersForClient(clientId);
    }

    @Override
    public Double getAverageValueOfOrder() {
        return repository.getAverageValueOfOrder();
    }

    @Override
    public Double getAverageValueOfOrderForClient(String clientId) {

        return repository.getAverageValueOfOrderForClient(clientId);
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

            return FXCollections.observableArrayList(repository.getOrdersForClient(clientId));

        }
    }
}
