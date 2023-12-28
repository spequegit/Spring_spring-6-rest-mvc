package speque.springframework.spring6restmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import speque.springframework.spring6restmvc.entity.Customer;
import speque.springframework.spring6restmvc.mapper.CustomerMapper;
import speque.springframework.spring6restmvc.model.CustomerDTO;
import speque.springframework.spring6restmvc.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Primary
@Service
public class CustomerServiceJPA implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO getCustomerById(UUID uuid) {
        Customer customer = customerRepository.findById(uuid).orElseThrow();
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public List<CustomerDTO> getCustomersList() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDto).toList();
    }
}
