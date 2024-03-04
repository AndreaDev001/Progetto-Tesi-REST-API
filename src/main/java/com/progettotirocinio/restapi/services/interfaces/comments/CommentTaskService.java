package com.progettotirocinio.restapi.services.interfaces.comments;

import com.progettotirocinio.restapi.data.dto.input.create.CreateCommentDto;
import com.progettotirocinio.restapi.data.dto.output.comments.CommentTaskDto;
import com.progettotirocinio.restapi.data.entities.comments.CommentTask;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface CommentTaskService
{
    PagedModel<CommentTaskDto> getComments(Pageable pageable);
    CollectionModel<CommentTaskDto> getCommentsByTask(UUID taskID);
    CommentTaskDto getCommentTaskByID(UUID taskID);
    CommentTaskDto getCommentByTaskAndPublisher(UUID taskID,UUID publisherID);
    CommentTaskDto createTaskComment(UUID taskID, CreateCommentDto createCommentDto);
    void deleteTaskComment(UUID taskCommentID);
}
