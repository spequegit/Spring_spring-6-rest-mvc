package speque.springframework.spring6restmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.entity.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> { // crudepository
}
