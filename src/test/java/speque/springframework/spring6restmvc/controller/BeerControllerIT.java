package speque.springframework.spring6restmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.mapper.BeerMapper;
import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.model.BeerStyle;
import speque.springframework.spring6restmvc.repository.BeerRepository;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testListBeersByNameAndStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("name", "IPA")
                        .queryParam("style", BeerStyle.IPA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }

    @Test
    void testListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("name", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }
    @Test
    void testListBeersByStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("style", BeerStyle.IPA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(548)));
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteNotFound(){
        assertThrows(NoSuchElementException.class, () -> {
            beerController.deleteBeer(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDelete(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteBeer(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThrows(NoSuchElementException.class, () -> {
            beerRepository.findById(beer.getId()).get();
        });
    }

    @Test
    @Transactional
    @Rollback
    void testUpdate(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerMapper.beerToBeerDto(beer);
        dto.setId(null);
        dto.setVersion(null);
        dto.setBeerName("updated");
        ResponseEntity responseEntity = beerController.updateBeer(beer.getId(), dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer updated = beerRepository.findById(beer.getId()).get();
        assertThat(updated.getBeerName()).isEqualTo("updated");
    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NoSuchElementException.class, ()->{
           beerController.updateBeer(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }


    @Rollback
    @Transactional
    @Test
    void testAddNewBeer(){
        BeerDTO dto = BeerDTO.builder().beerName("test name").build();
        ResponseEntity responseEntity = beerController.postBeer(dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        URI location = responseEntity.getHeaders().getLocation();
        assertThat(location).isNotNull();

        String[] split = location.getPath().split("/");
        UUID uuid = UUID.fromString(split[4]);
        Beer beer = beerRepository.findById(uuid).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.getBeerList(null, null);
        assertThat(dtos.size()).isEqualTo(2413);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.getBeerList(null, null);
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testGetBeerByIdNotFound(){
        assertThrows(NoSuchElementException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }
}
