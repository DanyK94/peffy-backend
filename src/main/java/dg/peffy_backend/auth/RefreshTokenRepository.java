package dg.peffy_backend.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.auth.entity.RefreshToken;
import jakarta.persistence.LockModeType;
import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
    
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT rt
        FROM RefreshToken rt
        WHERE rt.tokenHash = :tokenHash
    """)
    Optional<RefreshToken> findByTokenHashForUpdate(
        @Param("tokenHash") String tokenHash
    );

}
