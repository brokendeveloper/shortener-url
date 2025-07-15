package tech.brokendeveloper.shortener_url.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_acess")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    private LocalDateTime accessTime;
}
