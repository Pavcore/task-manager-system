package com.pavcore.task.management.system.dto.response;

import com.pavcore.task.management.system.dto.Role;
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

    private Role role;

}
