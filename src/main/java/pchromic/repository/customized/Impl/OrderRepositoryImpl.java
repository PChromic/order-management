package pchromic.repository.customized.Impl;

import org.springframework.stereotype.Repository;
import pchromic.domain.Order;
import pchromic.repository.customized.CustomizedOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl implements CustomizedOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getOrdersForClient(String clientId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);

        Root<Order> order = query.from(Order.class);
        query.select(order)
                .where(builder.equal(order.get("clientId"), clientId));

        TypedQuery<Order> tq = entityManager.createQuery(query);
        return tq.getResultList();
    }


    @Override
    public Integer getTotalAmountOfOrders() {
        Query query = entityManager.
                createQuery("SELECT o FROM Order o");
        List<String> list = query.getResultList();
        return list.size();
    }

    @Override
    public Double getTotalOrdersValue() {
        Query query = entityManager.
                createQuery("SELECT o.price FROM Order o");
        List<Double> list = query.getResultList();

        return list.stream().mapToDouble(d-> d).sum();
    }

    @Override
    public Double getTotalOrdersValueForClient(String clientId) {
        Query query = entityManager.
                createQuery("SELECT o.price FROM Order o WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);
        List<Double> list = query.getResultList();

        return list.stream().mapToDouble(d-> d).sum();
    }

    @Override
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT o FROM order o").getResultList();
    }

    @Override
    public Integer getOrdersAmountForClient(String clientId) {
        Query query = entityManager.
                createQuery("SELECT o.price FROM Order o WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);
        return query.getResultList().size();
    }

    @Override
    public Double getAverageValueOfOrder() {
        Query query = entityManager.
                createQuery("SELECT o.price FROM Order o");

        List<Double> list = query.getResultList();

        double averageValue = list.stream()
                .mapToDouble(d -> d)
                .sum() / list.size();

        return BigDecimal.valueOf(averageValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getAverageValueOfOrderForClient(String clientId) {
        Query query = entityManager.
                createQuery("SELECT o.price FROM Order o WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);
        List<Double> list = query.getResultList();

        double averageValue = list.stream()
                .mapToDouble(d -> d)
                .sum() / list.size();

        return BigDecimal.valueOf(averageValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
