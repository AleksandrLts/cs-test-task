package clear.solution.test.task.controller;

import clear.solution.test.task.dto.UserDto;
import clear.solution.test.task.dto.request.UserPatchRequestDto;
import clear.solution.test.task.dto.request.UserRequestDto;
import clear.solution.test.task.model.User;
import clear.solution.test.task.service.UserService;
import clear.solution.test.task.service.mapper.UserMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final static String DEFAULT_PAGE_NUMBER = "0";
	private final static String DEFAULT_PAGE_SIZE = "10";
	private final UserService userService;
	private final UserMapper userMapper;

	@PostMapping
	public ResponseEntity<UserDto> add(@RequestBody @Valid UserRequestDto userRequestDto) {
		User user = userService.add(userMapper.toUser(userRequestDto));
		return ResponseEntity.ok(userMapper.toDto(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id,
										  @RequestBody @Valid UserRequestDto userRequestDto) {
		User updatedUser = userService.update(id, userMapper.toUser(userRequestDto));
		return ResponseEntity.ok(userMapper.toDto(updatedUser));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserDto> patch(@PathVariable Long id,
										 @RequestBody @Valid UserPatchRequestDto userPatchRequestDto)
			throws IOException {
		User oldUser = userService.findUserById(id);
		User userToPatch = applyPatchToUser(userPatchRequestDto, oldUser);
        User patchedUser = userService.update(id, userToPatch);
		return ResponseEntity.ok(userMapper.toDto(patchedUser));
	}

	@GetMapping
	public ResponseEntity<Page<UserDto>> getUsersByBirthDateRange(@RequestParam LocalDate from,
																  @RequestParam LocalDate to,
																  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER)
																  int page,
																  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE)
																  int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> userResultPage = userService.getUsersByBirthDate(from, to, pageable);
		return ResponseEntity.ok(userResultPage.map(userMapper::toDto));
	}

	private User applyPatchToUser(UserPatchRequestDto userPatchRequestDto, User oldUser)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		JsonNode originalObjNode = objectMapper.valueToTree(oldUser);
		JsonNode patchNode = objectMapper.valueToTree(userPatchRequestDto);
		JsonNode mergedNode = mergeJsonNodes(originalObjNode, patchNode);
		return objectMapper.treeToValue(mergedNode, User.class);
	}

	private JsonNode mergeJsonNodes(JsonNode original, JsonNode patch) {
		ObjectNode mergedNode = original.deepCopy();
		Iterator<String> fieldNames = patch.fieldNames();
		while (fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			JsonNode newValue = patch.get(fieldName);
			if (!newValue.isNull()) {
				mergedNode.set(fieldName, newValue);
			}
		}
		return mergedNode;
	}
}
