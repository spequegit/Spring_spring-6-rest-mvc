package speque.springframework.spring6restmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String name);
    List<Beer> findAllByBeerStyle(BeerStyle style);
}
