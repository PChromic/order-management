package pchromic.domain;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlRootElement (name = "order")
@XmlType(
        propOrder = {
                "clientId",
                "requestId",
                "name",
                "quantity",
                "price"
        }
)
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @CsvBindByName(column = "Client_Id",required = true)
    @Column(nullable = false)
    private String clientId;

    @CsvBindByName(column = "Request_Id", required = true)
    @Column(nullable = false)
    private Long requestId;

    @CsvBindByName(column = "Name" ,required = true)
    @Column(nullable = false)
    private String name;

    @CsvBindByName(column = "Quantity",required = true)
    @Column(nullable = false)
    private Integer quantity;

    @CsvBindByName(column = "Price",required = true)
    @Column(nullable = false)
    private Double price;

    public Order() {
    }

    public Order(String clientId, Long requestId, String name, Integer quantity, Double price) {
        this.clientId = clientId;
        this.requestId = requestId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
