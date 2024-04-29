package clear.solution.test.task.repository;

import clear.solution.test.task.model.User;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> getUserByBirthDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    Optional<User> getUserByEmail(String email);

}
