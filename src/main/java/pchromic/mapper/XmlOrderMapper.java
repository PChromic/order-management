package pchromic.mapper;

import pchromic.domain.Order;
import pchromic.domain.Orders;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class XmlOrderMapper {

    public List<Order> map(File file) {

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Orders.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Orders orders = (Orders) jaxbUnmarshaller.unmarshal(file);

            return orders.getOrders();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
