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
        Query query = entityManager
                .createQuery(
                        "SELECT o " +
                        "FROM Order o " +
                        "WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);
        return query.getResultList();
    }


    @Override
    public Long getTotalAmountOfOrders() {
        Query query = entityManager
                .createQuery(
                        "SELECT COUNT(*) " +
                                "FROM Order AS o");
    return (Long)query.getSingleResult();
    }

    @Override
    public Double getTotalOrdersValue() {
        Query query = entityManager.
                createQuery(
                        "SELECT SUM(o.price) " +
                        "FROM Order o");
        List<Double> list = query.getResultList();

        return (Double)query.getSingleResult();
    }

    @Override
    public Double getTotalOrdersValueForClient(String clientId) {
        Query query = entityManager.
                createQuery(
                        "SELECT SUM(o.price) " +
                        "FROM Order o " +
                        "WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);

        return (Double)query.getSingleResult();
    }

    @Override
    public List<Order> getAllOrders() {
        Query query = entityManager
                .createQuery(
                        "SELECT o " +
                        "FROM Order o");
        return query.getResultList();
    }

    @Override
    public Integer getOrdersAmountForClient(String clientId) {
        Query query = entityManager
                .createQuery(
                        "SELECT COUNT(*) " +
                                "FROM Order AS o"+
                                "WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);
        return query.getResultList().size();
    }

    @Override
    public Double getAverageValueOfOrder() {
        Query query = entityManager.
                createQuery(
                        "SELECT AVG(o.price) " +
                                "FROM Order o");

        Double averageValue = (Double)query.getSingleResult();

        return BigDecimal.valueOf(averageValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public Double getAverageValueOfOrderForClient(String clientId) {
        Query query = entityManager.
                createQuery(
                        "SELECT AVG(o.price)" +
                                "FROM Order AS o " +
                                "WHERE o.clientId = :clientId")
                .setParameter("clientId",clientId);

        Double averageValue = (Double)query.getSingleResult();

        return BigDecimal.valueOf(averageValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
