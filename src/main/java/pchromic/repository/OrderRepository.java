package pchromic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pchromic.domain.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {

}
