package tech.brokendeveloper.shortener_url.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.brokendeveloper.shortener_url.domain.Url;
import tech.brokendeveloper.shortener_url.domain.UrlAccess;

import java.util.UUID;

@Repository
public interface UrlAcessRepository extends JpaRepository<UrlAccess, Long> {

    long countByUrl(Url url);

    @Query("SELECT COUNT(a) * 1.0 / COUNT(DISTINCT DATE(a.accessTime)) FROM UrlAccess a WHERE a.url.id = :urlId")
    Double avgPerDay(@Param("urlId") UUID urlId);

}
