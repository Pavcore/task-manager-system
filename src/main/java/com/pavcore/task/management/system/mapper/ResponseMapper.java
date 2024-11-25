package com.pavcore.task.management.system.mapper;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dto.response.CommentResponseTo;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.dto.response.UserResponseTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    public UserResponseTO map(User user);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "performer", ignore = true)
    public TaskResponseTO map(Task task);

    public CommentResponseTo map(Comment comment);

}
