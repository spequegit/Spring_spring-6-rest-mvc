package speque.springframework.spring6restmvc.service;

import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID uuid);

    public List<BeerDTO> getBeerList(String name, BeerStyle style);

    public void deleteBeer(UUID beerId);

    public BeerDTO updateBeer(UUID id, BeerDTO beer);

    public BeerDTO addBeer(BeerDTO beer);

    public void patchBeer(UUID beerId, BeerDTO beer);
}
