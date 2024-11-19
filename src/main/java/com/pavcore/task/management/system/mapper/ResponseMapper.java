package com.pavcore.task.management.system.mapper;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.dto.response.UserResponseTO;
import org.mapstruct.Mapper;

@Mapper
public interface ResponseMapper {

    public User map(UserResponseTO userResponseTO);

    public UserResponseTO map(User user);

    public Task map(TaskResponseTO taskResponseTO);

    public TaskResponseTO map(Task task);

}
