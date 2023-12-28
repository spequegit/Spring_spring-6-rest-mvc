package speque.springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.entity.Customer;
import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.model.CustomerDTO;
import speque.springframework.spring6restmvc.repository.CustomerRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.getCustomerList();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.getCustomerList();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById(){
        Customer c = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(c.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testGetCustomerByIdNotFound(){
        assertThrows(NoSuchElementException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}
