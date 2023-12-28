package speque.springframework.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.service.BeerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/beer")
public class BeerController {
    public static final String BEER_ID = "{beerId}";
    public static final String BEER_ID1 = "beerId";
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> getBeerList(){
        return beerService.getBeerList();
    }

    @GetMapping(value = BEER_ID)
    public BeerDTO getBeerById(@PathVariable(BEER_ID1) UUID beerId){
        log.debug("--- get beer");
        return beerService.getBeerById(beerId).orElseThrow();
    }
    @PostMapping
    public ResponseEntity postBeer(@Validated @RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.addBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = BEER_ID)
    public ResponseEntity updateBeer(@PathVariable(BEER_ID1) UUID beerId, @RequestBody BeerDTO beer){
        log.debug("--- update beer");
        beerService.updateBeer(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_ID)
    public ResponseEntity deleteBeer(@PathVariable(BEER_ID1) UUID beerId){
        log.debug("--- delete beer");
        beerService.deleteBeer(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_ID)
    public ResponseEntity patchBeer(@PathVariable(BEER_ID1) UUID beerId, @RequestBody BeerDTO beer){
        beerService.patchBeer(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
