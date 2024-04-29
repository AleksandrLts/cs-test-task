package clear.solution.test.task.service.mapper;

import clear.solution.test.task.dto.UserDto;
import clear.solution.test.task.dto.request.UserRequestDto;
import clear.solution.test.task.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
		injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
	User toUser(UserRequestDto userRequestDto);

	UserDto toDto(User user);

}
