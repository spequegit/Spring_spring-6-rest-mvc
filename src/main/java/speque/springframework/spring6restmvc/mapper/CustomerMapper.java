package speque.springframework.spring6restmvc.mapper;

import org.mapstruct.Mapper;
import speque.springframework.spring6restmvc.entity.Customer;
import speque.springframework.spring6restmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
