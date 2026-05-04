package desafio.backend.infrastructure.persistence.entities;

import desafio.backend.domain.user.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String fullName;
    @Pattern(regexp = "\\d{11}|\\d{14}")
    @Column(unique = true, nullable = false)
    private String cpfCnpj;
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne
    private WalletEntity wallet;
}
