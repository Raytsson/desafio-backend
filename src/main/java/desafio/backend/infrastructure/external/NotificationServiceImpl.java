package desafio.backend.infrastructure.external;

import desafio.backend.application.NotificationService;
import desafio.backend.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notify(User user) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://util.devi.tools/api/v1/notify",
                user,
                Map.class
        );
    }
}
