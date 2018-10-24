package pchromic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pchromic.domain.Order;
import pchromic.repository.customized.CustomizedOrderRepository;

/**
 * Interface of CRUD and customized queries
 */
public interface OrderRepository extends JpaRepository<Order,Integer>,CustomizedOrderRepository {


}
