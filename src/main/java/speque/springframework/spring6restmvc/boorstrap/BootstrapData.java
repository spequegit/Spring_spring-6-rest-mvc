package speque.springframework.spring6restmvc.boorstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.entity.Customer;
import speque.springframework.spring6restmvc.model.BeerStyle;
import speque.springframework.spring6restmvc.repository.BeerRepository;
import speque.springframework.spring6restmvc.repository.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {
        if(customerRepository.count() == 0){

            Customer customer1 = Customer.builder().id(UUID.randomUUID())
                    .name("Cus 1").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
            Customer customer2 = Customer.builder().id(UUID.randomUUID())
                    .name("Cus 2").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
            Customer customer3 = Customer.builder().id(UUID.randomUUID())
                    .name("Cus 3").version(1).createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }
    }

    private void loadBeerData() {
        if(beerRepository.count() == 0){

            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }
}
