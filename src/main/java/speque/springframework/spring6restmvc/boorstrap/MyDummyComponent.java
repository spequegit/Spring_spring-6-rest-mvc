package speque.springframework.spring6restmvc.boorstrap;

import org.springframework.stereotype.Component;

@Component
public class MyDummyComponent {
    public MyDummyComponent() {
        System.out.println("czy to sie wykona");
    }
}
