package work.ngochuyen.spring.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.ngochuyen.spring.model.User;


import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm User theo ID
    Optional<User> findById(Long id);
}
