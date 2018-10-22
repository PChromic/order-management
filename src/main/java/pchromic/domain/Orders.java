package pchromic.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "orders")
public class Orders {

    private List<Order> orders;

    public Orders() {
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @XmlElement (name = "order")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}