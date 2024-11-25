package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.TaskRepo;
import com.pavcore.task.management.system.dto.Role;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.exception.NoAccessException;
import com.pavcore.task.management.system.exception.NotFoundUserException;
import com.pavcore.task.management.system.mapper.RequestMapper;
import com.pavcore.task.management.system.mapper.ResponseMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private CommentService commentService;

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private UserService userService;

    @Mock
    private ResponseMapper responseMapper;

    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private TaskService taskService;

    private TaskRequestTO taskRequestTO;
    private Task task;
    private TaskResponseTO taskResponseTO;
    private User author;
    private User performer;

    @BeforeEach
    public void setUp() {
        taskRequestTO = new TaskRequestTO();
        taskRequestTO.setAuthor(1L);
        taskRequestTO.setPerformer(2L);
        taskRequestTO.setComment("Test comment");

        author = new User();
        author.setId(1L);
        author.setRole(Role.ADMIN);

        performer = new User();
        performer.setId(2L);
        performer.setRole(Role.USER);

        task = new Task();
        task.setId(1L);
        task.setAuthor(author);
        task.setPerformer(performer);

        taskResponseTO = new TaskResponseTO();
        taskResponseTO.setId(1L);
        taskResponseTO.setAuthor(1L);
        taskResponseTO.setPerformer(2L);
    }

    @Test
    public void testCreateTask_Success() {
        when(requestMapper.map(taskRequestTO)).thenReturn(task);
        when(userService.getUserById(1L)).thenReturn(author);
        when(userService.getUserById(2L)).thenReturn(performer);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.createTask(taskRequestTO);

        assertNotNull(result);
        assertEquals(1L, result.getAuthor());
        verify(taskRepo, times(1)).save(task);
        verify(commentService, times(1)).save(any(Comment.class));
    }

    @Test
    public void testCreateTask_AuthorNotFound() {
        when(userService.getUserById(1L)).thenThrow(new NotFoundUserException("Author not found"));

        NotFoundUserException exception = assertThrows(NotFoundUserException.class, () -> taskService.createTask(taskRequestTO));
        assertEquals("Author not found", exception.getMessage());
        verify(taskRepo, times(0)).save(any(Task.class));
    }


    @Test
    public void testUpdateTask_Success() {
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(requestMapper.map(taskRequestTO)).thenReturn(task);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.updateTask(taskRequestTO, 1L);

        assertNotNull(result);
        verify(taskRepo, times(1)).save(task);
    }

    @Test
    public void testUpdateTask_NotFound() {
        when(taskRepo.findById(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(taskRequestTO, 1L));
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepo, times(0)).save(any(Task.class));
    }


    @Test
    public void testGetTask_Success() {
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.getTask(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetTask_NotFound() {
        when(taskRepo.findById(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> taskService.getTask(1L));
        assertEquals("Task not found", exception.getMessage());
    }


    @Test
    public void testDeleteTask_Success() {
        doNothing().when(taskRepo).deleteById(1L);

        boolean result = taskService.deleteTask(1L);

        assertTrue(result);
        verify(taskRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTask_NotFound() {
        doThrow(new EntityNotFoundException("Task not found")).when(taskRepo).deleteById(1L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(1L));
        assertEquals("Task not found", exception.getMessage());
    }


    @Test
    public void testChangeStatus_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(userService.getUserByToken(request)).thenReturn(performer);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.changeStatus("In Progress", 1L, request);

        assertNotNull(result);
        verify(taskRepo, times(1)).save(task);
        assertEquals("In Progress", task.getStatus());
    }

    @Test
    public void testChangeStatus_NoAccess() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        User anotherUser = new User();
        anotherUser.setId(3L);
        anotherUser.setRole(Role.USER);

        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(userService.getUserByToken(request)).thenReturn(anotherUser);

        NoAccessException exception = assertThrows(NoAccessException.class, () -> taskService.changeStatus("In Progress", 1L, request));
        assertEquals("No access", exception.getMessage());
        verify(taskRepo, times(0)).save(any(Task.class));
    }


    @Test
    public void testChangePriority_Success() {
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.changePriority("High", 1L);

        assertNotNull(result);
        verify(taskRepo, times(1)).save(task);
        assertEquals("High", task.getPriority());
    }

    @Test
    public void testChangePriority_NotFound() {
        when(taskRepo.findById(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> taskService.changePriority("High", 1L));
        assertEquals("Task not found", exception.getMessage());
    }


    @Test
    public void testChangePerformer_Success() {
        User newPerformer = new User();
        newPerformer.setId(3L);
        newPerformer.setRole(Role.USER);

        when(userService.getUserById(3L)).thenReturn(newPerformer);
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.changePerformer(3L, 1L);

        assertNotNull(result);
        verify(taskRepo, times(1)).save(task);
        assertEquals(3L, task.getPerformer().getId());
    }

    @Test
    public void testChangePerformer_TaskNotFound() {
        when(taskRepo.findById(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> taskService.changePerformer(3L, 1L));
        assertEquals("Task not found", exception.getMessage());
    }


    @Test
    public void testChangeComment_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(userService.getUserByToken(request)).thenReturn(performer);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        TaskResponseTO result = taskService.changeComment("New comment", 1L, request);

        assertNotNull(result);
        verify(commentService, times(1)).save(any(Comment.class));
    }

    @Test
    public void testChangeComment_NoAccess() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        User anotherUser = new User();
        anotherUser.setId(3L);
        anotherUser.setRole(Role.USER);

        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(userService.getUserByToken(request)).thenReturn(anotherUser);

        NoAccessException exception = assertThrows(NoAccessException.class, () -> taskService.changeComment("New comment", 1L, request));
        assertEquals("No access", exception.getMessage());
        verify(commentService, times(0)).save(any(Comment.class));
    }


    @Test
    public void testGetTasksByAuthor_Success() {
        List<Task> tasks = Collections.singletonList(task);
        when(userService.getUserById(1L)).thenReturn(author);
        when(taskRepo.getTasksByAuthor(author, PageRequest.of(1, 5))).thenReturn(tasks);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        List<TaskResponseTO> result = taskService.getTasksByAuthor(1L, 1, 5);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetTasksByAuthor_AuthorNotFound() {
        when(userService.getUserById(1L)).thenThrow(new NotFoundUserException("Author not found"));

        NotFoundUserException exception = assertThrows(NotFoundUserException.class, () -> taskService.getTasksByAuthor(1L, 1, 5));
        assertEquals("Author not found", exception.getMessage());
    }


    @Test
    public void testGetTasksByPerformer_Success() {
        List<Task> tasks = Collections.singletonList(task);
        when(userService.getUserById(2L)).thenReturn(performer);
        when(taskRepo.getTasksByPerformer(performer, PageRequest.of(1, 5))).thenReturn(tasks);
        when(responseMapper.map(task)).thenReturn(taskResponseTO);

        List<TaskResponseTO> result = taskService.getTasksByPerformer(2L, 1, 5);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetTasksByPerformer_PerformerNotFound() {
        when(userService.getUserById(2L)).thenThrow(new NotFoundUserException("Performer not found"));

        NotFoundUserException exception = assertThrows(NotFoundUserException.class, () -> taskService.getTasksByPerformer(2L, 1, 5));
        assertEquals("Performer not found", exception.getMessage());
    }
}
