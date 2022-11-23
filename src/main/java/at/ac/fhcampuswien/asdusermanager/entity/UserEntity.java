package at.ac.fhcampuswien.asdusermanager.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    Long userId;
    @Length(min = 3, max = 30, message = "First name must be {min} and {max} characters long")
    @Column(name = "TXT_FIRSTNAME")
    String firstName;
    @Length(min = 3, max = 30, message = "Last name must be {min} and {max} characters long")
    @Column(name = "TXT_LASTNAME")
    String lastName;
    @Length(min = 3, max = 32, message = "Username must be {min} and {max} characters long")
    @Column(name = "TXT_USERNAME", unique = true)
    String userName;
    @Length(min = 1, max = 255, message = "Password must be {min} and {max} characters long")
    @Column(name = "HASH_PASSWORD")
    String password;
    @Column(name = "TST_CREATED")
    LocalDateTime created;
}
