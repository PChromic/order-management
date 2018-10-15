package pchromic.mapper;


import org.springframework.stereotype.Component;
import pchromic.domain.Order;
import pchromic.domain.OrderBuilder;

@Component
public class CsvOrderMapper {

    public Order map(String[] csvOrder){

        return OrderBuilder.anOrder()
                .withClientId(csvOrder[0])
                .withRequestId(Integer.parseInt(csvOrder[1]))
                .withName(csvOrder[2])
                .withQuantity(Integer.parseInt(csvOrder[3]))
                .withPrice(Double.parseDouble(csvOrder[4]))
                .build();
    }


}

