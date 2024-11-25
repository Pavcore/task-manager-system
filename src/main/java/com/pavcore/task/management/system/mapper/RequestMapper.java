package com.pavcore.task.management.system.mapper;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.request.UserRequestTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    public User map(UserRequestTO userRequestTO);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "performer", ignore = true)
    public Task map(TaskRequestTO taskRequestTO);

}
