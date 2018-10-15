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
import pchromic.validator.Validator;
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    Validator validator;

    @Autowired
    CsvOrderMapper mapper;

    @Override
    public void addOrder(Order order) {
        repository.save(order);
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
