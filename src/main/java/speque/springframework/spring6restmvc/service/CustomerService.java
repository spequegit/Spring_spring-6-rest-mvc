package speque.springframework.spring6restmvc.service;

import speque.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID uuid);
    public List<CustomerDTO> getCustomersList();
}
