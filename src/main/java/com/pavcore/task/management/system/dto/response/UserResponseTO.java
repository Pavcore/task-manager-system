package com.pavcore.task.management.system.dto.response;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseTO {

    private long id;

    private String email;

    private Role role;

    private List<Task> task;

}
