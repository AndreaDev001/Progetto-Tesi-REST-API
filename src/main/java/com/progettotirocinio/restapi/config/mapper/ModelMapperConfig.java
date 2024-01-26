package com.progettotirocinio.restapi.config.mapper;

import com.progettotirocinio.restapi.data.dto.output.GenericOutput;
import com.progettotirocinio.restapi.data.dto.output.UserDto;
import com.progettotirocinio.restapi.data.dto.output.refs.*;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.data.entities.interfaces.OwnableEntity;
import org.modelmapper.*;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class ModelMapperConfig
{
    private final Converter<Board, BoardRef> boardRefConverter = new AbstractConverter<Board, BoardRef>() {
        @Override
        protected BoardRef convert(Board board) {
            return new BoardRef(board);
        }
    };

    private final Converter<Discussion,DiscussionRef> discussionRefConverter = new AbstractConverter<Discussion, DiscussionRef>() {
        @Override
        protected DiscussionRef convert(Discussion discussion) {
            return new DiscussionRef(discussion);
        }
    };

    private final Converter<Role, RoleRef> roleRefConverter = new AbstractConverter<Role,RoleRef>() {
        @Override
        protected RoleRef convert(Role role) {
            return new RoleRef(role);
        }
    };
    private final Converter<TaskGroup, TaskGroupRef> taskGroupRefConverter = new AbstractConverter<TaskGroup, TaskGroupRef>() {
        @Override
        protected TaskGroupRef convert(TaskGroup taskGroup) {
            return new TaskGroupRef(taskGroup);
        }
    };
    private final Converter<Task,TaskRef> taskRefConverter = new AbstractConverter<Task, TaskRef>() {
        @Override
        protected TaskRef convert(Task task) {
            return new TaskRef(task);
        }
    };

    private final Converter<Team, TeamRef> teamRefConverter = new AbstractConverter<Team,TeamRef>() {
        @Override
        protected TeamRef convert(Team team) {
            return new TeamRef(team);
        }
    };
    private final Converter<Poll,PollRef> pollRefConverter = new AbstractConverter<Poll, PollRef>() {
        @Override
        protected PollRef convert(Poll poll) {
            return new PollRef(poll);
        }
    };
    private final Converter<Comment,CommentRef> commentRefConverter = new AbstractConverter<Comment, CommentRef>() {
        @Override
        protected CommentRef convert(Comment comment) {
            return new CommentRef(comment);
        }
    };
    private final Converter<User,UserRef> userRefConverter = new AbstractConverter<User, UserRef>() {
        @Override
        protected UserRef convert(User user) {
            return new UserRef(user);
        }
    };
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        modelMapper.addConverter(boardRefConverter);
        modelMapper.addConverter(discussionRefConverter);
        modelMapper.addConverter(roleRefConverter);
        modelMapper.addConverter(taskGroupRefConverter);
        modelMapper.addConverter(taskRefConverter);
        modelMapper.addConverter(teamRefConverter);
        modelMapper.addConverter(userRefConverter);
        modelMapper.addConverter(commentRefConverter);
        modelMapper.addConverter(pollRefConverter);
        return modelMapper;
    }
}
