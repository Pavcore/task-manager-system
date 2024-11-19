package com.pavcore.task.management.system.mapper;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.request.UserRequestTO;
import org.mapstruct.Mapper;

@Mapper
public interface RequestMapper {

    public User map(UserRequestTO userRequestTO);

    public UserRequestTO map(User user);

    public Task map(TaskRequestTO taskRequestTO);

    public TaskRequestTO map(Task task);
}
