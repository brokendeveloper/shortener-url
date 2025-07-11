package tech.brokendeveloper.shortener_url.domain.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UrlRepository extends JpaRepository<Url, UUID> {


}
