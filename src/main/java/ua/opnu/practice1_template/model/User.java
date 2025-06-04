package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private String username;
    private String password;
    private String role;
}
