package com.progettotirocinio.restapi.config.mapper;

import com.progettotirocinio.restapi.data.dto.output.refs.*;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.data.entities.checklists.CheckList;
import com.progettotirocinio.restapi.data.entities.comments.Comment;
import com.progettotirocinio.restapi.data.entities.polls.Poll;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    private static final Converter<Tag,TagRef> tagRefConverter = new AbstractConverter<Tag, TagRef>() {
        @Override
        protected TagRef convert(Tag tag) {
            return new TagRef(tag);
        }
    };
    private static final Converter<BoardMember,BoardMemberRef> boardMemberRefConverter = new AbstractConverter<BoardMember, BoardMemberRef>() {
        @Override
        protected BoardMemberRef convert(BoardMember member) {
            return new BoardMemberRef(member);
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
        modelMapper.addConverter(tagRefConverter);
        modelMapper.addConverter(boardMemberRefConverter);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return generateModelMapper();
    }
}
