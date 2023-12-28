package speque.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private UUID id;
    private Integer version;
}
