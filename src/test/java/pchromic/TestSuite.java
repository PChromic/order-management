package pchromic;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pchromic.repository.OrderRepositoryTest;
import pchromic.service.OrderServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderServiceTest.class,
        OrderRepositoryTest.class,

   })
public class TestSuite {
}
