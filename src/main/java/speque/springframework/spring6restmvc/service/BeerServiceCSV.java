package speque.springframework.spring6restmvc.service;

import org.springframework.stereotype.Service;
import speque.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerServiceCSV {
    public List<BeerCSVRecord> convertCSV(File csv);
}
