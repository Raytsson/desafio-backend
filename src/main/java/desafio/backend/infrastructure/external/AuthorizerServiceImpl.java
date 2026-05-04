package desafio.backend.infrastructure.external;

import desafio.backend.application.AuthorizerService;
import desafio.backend.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizerServiceImpl implements AuthorizerService {

    @Override
    public boolean authorize(User payer, BigDecimal value) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.getForEntity(
                "https://util.devi.tools/api/v2/authorize",
                Map.class
        );

        return response.getStatusCode().is2xxSuccessful();
    }
}