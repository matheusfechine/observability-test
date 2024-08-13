package br.com.performance.observability.repository;

import br.com.performance.observability.model.User;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed(name = "UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
}
