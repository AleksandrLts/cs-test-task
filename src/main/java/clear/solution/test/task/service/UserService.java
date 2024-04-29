package clear.solution.test.task.service;

import clear.solution.test.task.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

public interface UserService {
    User add(User user);

    User update(Long id, User user);

    void delete(Long id);

    Page<User> getUsersByBirthDate(LocalDate from, LocalDate to, Pageable pageable);

    User findUserByPhoneNumber(String phoneNumber);

    User findUserById(Long id);

    User findUserByEmail(String email);
}
