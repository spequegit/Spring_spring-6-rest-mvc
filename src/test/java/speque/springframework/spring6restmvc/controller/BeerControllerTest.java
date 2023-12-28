package speque.springframework.spring6restmvc.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.dialect.MySQLDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.service.BeerService;
import speque.springframework.spring6restmvc.service.BeerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Captor
    ArgumentCaptor<UUID> argumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testNullBeerName() throws Exception {
        BeerDTO dto = BeerDTO.builder().build();

        given(beerService.addBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.getBeerList().get(1));

        MvcResult mvcResult = mockMvc.perform(post("/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateNewBeer2() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);

        given(beerService.addBeer(any())).willReturn(beer);

        mockMvc.perform(post("/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        given(beerService.updateBeer(any(),any())).willReturn(beer);
        mockMvc.perform(put("/beer/"+beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)));
//                        .andExpect(status().isNoContent());
        verify(beerService).updateBeer(beer.getId(),beer);
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);

        mockMvc.perform(delete("/beer/"+beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteBeer(argumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch("/beer/" + beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeer(argumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testCreateNewBeer() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        BeerDTO beer = beerServiceImpl.getBeerList().get(0);
        System.out.println(objectMapper.writeValueAsString(beer));
    }

    @Test
    void testGetBeerById() throws Exception {

        BeerDTO beer = beerServiceImpl.getBeerList().get(0);

//        given(beerService.getBeerById(any())).willReturn(beer);
        when(beerService.getBeerById(any())).thenReturn(Optional.ofNullable(beer));

        mockMvc.perform(get("/beer/" + beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(beer.getBeerName())));
    }

    @Test
    
    void testListBeers() throws Exception {


        given(beerService.getBeerList()).willReturn(beerServiceImpl.getBeerList());

        mockMvc.perform(get("/beer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }
}
