package speque.springframework.spring6restmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import speque.springframework.spring6restmvc.entity.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
