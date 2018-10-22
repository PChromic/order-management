package pchromic.domain;

public final class OrderBuilder {
    private String clientId;
    private long requestId;
    private String name;
    private int quantity;
    private double price;


    private OrderBuilder() {
    }


    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OrderBuilder withRequestId(long requestId) {
        this.requestId = requestId;
        return this;
    }

    public OrderBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setClientId(clientId);
        order.setRequestId(requestId);
        order.setName(name);
        order.setQuantity(quantity);
        order.setPrice(price);
        return order;
    }
}
