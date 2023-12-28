package speque.springframework.spring6restmvc.repository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import speque.springframework.spring6restmvc.boorstrap.BootstrapData;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.model.BeerStyle;
import speque.springframework.spring6restmvc.service.BeerServiceCSVImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerServiceCSVImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        assertThat(list.size()).isEqualTo(336);
    }
    @Test
    void testGetBeerListByStyle(){
        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.IPA);
        assertThat(list.size()).isEqualTo(548);
    }

    @Test
    void testSaveBeerTooLong(){
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("test beer with long name 123123123123123123123123123123123123123123123")
                        .beerStyle(BeerStyle.GOSE)
                        .upc("123")
                        .quantityOnHand(4)
                        .price(BigDecimal.valueOf(20.99))
                .build());
//        assertThrows(DataIntegrityViolationException.class, () -> {
        assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer(){
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("test beer with long name")
                        .beerStyle(BeerStyle.GOSE)
                        .upc("123")
                        .quantityOnHand(4)
                        .price(BigDecimal.valueOf(20.99))
                .build());
//      beerRepository.flush(); // bez tego nie zadziala constraint db
        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

}
