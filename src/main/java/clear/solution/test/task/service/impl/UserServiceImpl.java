package clear.solution.test.task.service.impl;

import clear.solution.test.task.exception.DateRangeException;
import clear.solution.test.task.exception.UserNotFoundException;
import clear.solution.test.task.model.User;
import clear.solution.test.task.repository.UserRepository;
import clear.solution.test.task.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public User add(User user) {
		return userRepository.save(user);
	}

    @Transactional
	@Override
	public User update(Long id, User user) {
		User userToUpdate = findUserById(id);
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setBirthDate(user.getBirthDate());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setAddress(user.getAddress());

		return userRepository.save(userToUpdate);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		findUserById(id);
		userRepository.deleteById(id);
	}

	@Override
	public Page<User> getUsersByBirthDate(LocalDate from, LocalDate to, Pageable pageable) {
		if (from.isAfter(to)) {
			throw new DateRangeException("Invalid range of birth dates");
		}
		return userRepository.getUserByBirthDateBetween(from, to, pageable);
	}

	@Override
	public User findUserByPhoneNumber(String phoneNumber) {
		return userRepository.getUserByPhoneNumber(phoneNumber).orElse(null);
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("There is no user with id: " + id)
		);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.getUserByEmail(email).orElse(null);
	}
}
