package pchromic.repository.customized.Impl;

import org.springframework.stereotype.Repository;
import pchromic.domain.Order;
import pchromic.repository.customized.CustomizedOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl implements CustomizedOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> getOrdersByClientId(String clientId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);

        Root<Order> order = query.from(Order.class);
        query.select(order)
                .where(builder.equal(order.get("clientId"), clientId));

        TypedQuery<Order> tq = entityManager.createQuery(query);
        return tq.getResultList();
    }


}
