package com.pavcore.task.management.system.dto.response;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dto.Role;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseTO {

    private long id;

    private String email;

    private String password;

    private Role role;

    private List<Task> task;

}
