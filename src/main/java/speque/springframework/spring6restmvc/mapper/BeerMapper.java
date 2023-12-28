package speque.springframework.spring6restmvc.mapper;

import org.mapstruct.Mapper;
import speque.springframework.spring6restmvc.entity.Beer;
import speque.springframework.spring6restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
