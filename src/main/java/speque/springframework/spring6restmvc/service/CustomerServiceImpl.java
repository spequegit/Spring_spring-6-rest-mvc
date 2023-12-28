package speque.springframework.spring6restmvc.service;

import org.springframework.stereotype.Service;
import speque.springframework.spring6restmvc.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Cus 1").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
        CustomerDTO customer2 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Cus 2").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
        CustomerDTO customer3 = CustomerDTO.builder().id(UUID.randomUUID())
                .name("Cus 3").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(),customer1);
        customerMap.put(customer2.getId(),customer2);
        customerMap.put(customer3.getId(),customer3);
    }

    @Override
    public CustomerDTO getCustomerById(UUID uuid) {
        return customerMap.get(uuid);
    }

    @Override
    public List<CustomerDTO> getCustomersList() {
        return customerMap.values().stream().toList();
    }
}
