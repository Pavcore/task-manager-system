package com.pavcore.task.management.system.mapper;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "performer", ignore = true)
    Task map(TaskRequestTO taskRequestTO);

}
