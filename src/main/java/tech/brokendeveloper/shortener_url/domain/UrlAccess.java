package tech.brokendeveloper.shortener_url.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_access")
@Getter
@Setter
@NoArgsConstructor
public class UrlAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    private LocalDateTime accessTime;

    public UrlAccess(Url url, LocalDateTime accessTime) {
        this.url = url;
        this.accessTime = accessTime;
    }
}
