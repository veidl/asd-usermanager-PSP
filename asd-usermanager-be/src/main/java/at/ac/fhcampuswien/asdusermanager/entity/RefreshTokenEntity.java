package at.ac.fhcampuswien.asdusermanager.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "REFRESH_TOKEN")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REFRESH_TOKEN")
    Long id;
    @Column(name = "TXT_TOKEN")
    String token;
    @Column(name = "TST_CREATED")
    Instant createdDate;
}
