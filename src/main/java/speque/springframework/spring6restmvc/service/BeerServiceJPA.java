package speque.springframework.spring6restmvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.mapper.BeerMapper;
import speque.springframework.spring6restmvc.model.BeerDTO;
import speque.springframework.spring6restmvc.repository.BeerRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID uuid) {
        Beer beer = beerRepository.findById(uuid).orElse(null);
        return Optional.ofNullable(beerMapper.beerToBeerDto(beer));
    }

    @Override
    public List<BeerDTO> getBeerList() {
        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDto).toList();
    }

    @Override
    public void deleteBeer(UUID beerId) {
        beerRepository.findById(beerId).orElseThrow();
        beerRepository.deleteById(beerId);
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beer) {
        Beer found = beerRepository.findById(id).orElseThrow();
        found.setBeerName(beer.getBeerName());
        found.setBeerStyle(beer.getBeerStyle());
        found.setUpc(beer.getUpc());
        found.setPrice(beer.getPrice());
        return beerMapper.beerToBeerDto(beerRepository.save(found));
    }

    @Override
    public BeerDTO addBeer(BeerDTO dto) {
        Beer beer = beerMapper.beerDtoToBeer(dto);
        Beer save = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(save);
    }

    @Override
    public void patchBeer(UUID beerId, BeerDTO beer) {

    }
}
