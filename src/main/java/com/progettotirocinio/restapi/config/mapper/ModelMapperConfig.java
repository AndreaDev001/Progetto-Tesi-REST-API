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
    private static final Converter<Board, BoardRef> boardRefConverter = new AbstractConverter<Board, BoardRef>() {
        @Override
        protected BoardRef convert(Board board) {
            return new BoardRef(board);
        }
    };

    private static final Converter<Discussion,DiscussionRef> discussionRefConverter = new AbstractConverter<Discussion, DiscussionRef>() {
        @Override
        protected DiscussionRef convert(Discussion discussion) {
            return new DiscussionRef(discussion);
        }
    };

    private static final Converter<Role, RoleRef> roleRefConverter = new AbstractConverter<Role,RoleRef>() {
        @Override
        protected RoleRef convert(Role role) {
            return new RoleRef(role);
        }
    };
    private static final Converter<TaskGroup, TaskGroupRef> taskGroupRefConverter = new AbstractConverter<TaskGroup, TaskGroupRef>() {
        @Override
        protected TaskGroupRef convert(TaskGroup taskGroup) {
            return new TaskGroupRef(taskGroup);
        }
    };
    private static final Converter<Task,TaskRef> taskRefConverter = new AbstractConverter<Task, TaskRef>() {
        @Override
        protected TaskRef convert(Task task) {
            return new TaskRef(task);
        }
    };

    private static final Converter<Team, TeamRef> teamRefConverter = new AbstractConverter<Team,TeamRef>() {
        @Override
        protected TeamRef convert(Team team) {
            return new TeamRef(team);
        }
    };
    private static final Converter<Poll,PollRef> pollRefConverter = new AbstractConverter<Poll, PollRef>() {
        @Override
        protected PollRef convert(Poll poll) {
            return new PollRef(poll);
        }
    };
    private static final Converter<Comment,CommentRef> commentRefConverter = new AbstractConverter<Comment, CommentRef>() {
        @Override
        protected CommentRef convert(Comment comment) {
            return new CommentRef(comment);
        }
    };
    private static final Converter<User,UserRef> userRefConverter = new AbstractConverter<User, UserRef>() {
        @Override
        protected UserRef convert(User user) {
            return new UserRef(user);
        }
    };
    private static final Converter<CheckList,CheckListRef> checkListRefConverter = new AbstractConverter<CheckList, CheckListRef>() {
        @Override
        protected CheckListRef convert(CheckList checkList) {
            return new CheckListRef(checkList);
        }
    };

    public static ModelMapper generateModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addConverter(boardRefConverter);
        modelMapper.addConverter(discussionRefConverter);
        modelMapper.addConverter(roleRefConverter);
        modelMapper.addConverter(taskGroupRefConverter);
        modelMapper.addConverter(taskRefConverter);
        modelMapper.addConverter(teamRefConverter);
        modelMapper.addConverter(userRefConverter);
        modelMapper.addConverter(commentRefConverter);
        modelMapper.addConverter(pollRefConverter);
        modelMapper.addConverter(checkListRefConverter);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return generateModelMapper();
    }
}
