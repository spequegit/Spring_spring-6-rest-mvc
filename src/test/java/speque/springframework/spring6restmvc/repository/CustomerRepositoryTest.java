package speque.springframework.spring6restmvc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import speque.springframework.spring6restmvc.entity.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository CustomerRepository;

    @Test
    void testSaveCustomer(){
        Customer Customer = CustomerRepository.save(speque.springframework.spring6restmvc.entity.Customer.builder().name("test Customer").build());
        assertThat(Customer).isNotNull();
        assertThat(Customer.getId()).isNotNull();
    }

}
