package speque.springframework.spring6restmvc.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import speque.springframework.spring6restmvc.model.CustomerDTO;
import speque.springframework.spring6restmvc.service.CustomerService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> getCustomerList(){
        return customerService.getCustomersList();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId){
        return customerService.getCustomerById(customerId);
    }
}
