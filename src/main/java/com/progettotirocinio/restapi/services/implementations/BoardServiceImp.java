package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.*;
import com.progettotirocinio.restapi.data.dao.specifications.BoardSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dao.tags.TagDao;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBoardDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.*;
import com.progettotirocinio.restapi.data.entities.enums.BoardStatus;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import com.progettotirocinio.restapi.data.entities.enums.PermissionType;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import com.progettotirocinio.restapi.data.entities.tags.Tag;
import com.progettotirocinio.restapi.services.interfaces.BoardService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_BOARDS",allSearchName = "SEARCH_BOARDS",searchCachingRequired = true)
public class BoardServiceImp extends GenericServiceImp<Board, BoardDto> implements BoardService  {

    private final BoardDao boardDao;
    private final RoleDao roleDao;
    private final TagDao tagDao;
    private final RoleOwnerDao roleOwnerDao;
    private final TaskGroupDao taskGroupDao;
    private final BoardMemberDao boardMemberDao;
    private final PermissionDao permissionDao;

    public BoardServiceImp(BoardMemberDao boardMemberDao,PermissionDao permissionDao,TagDao tagDao,RoleDao roleDao,RoleOwnerDao roleOwnerDao,TaskGroupDao taskGroupDao,CacheHandler cacheHandler,UserDao userDao, BoardDao boardDao, Mapper mapper, PagedResourcesAssembler<Board> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,Board.class,BoardDto.class,pagedResourcesAssembler);
        this.permissionDao = permissionDao;
        this.boardDao = boardDao;
        this.taskGroupDao = taskGroupDao;
        this.boardMemberDao = boardMemberDao;
        this.roleDao = roleDao;
        this.roleOwnerDao = roleOwnerDao;
        this.tagDao = tagDao;
    }

    @Override
    public PagedModel<BoardDto> getBoards(Pageable pageable) {
        Page<Board> boards = this.boardDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByPublisher(UUID publisherID, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByPublisher(publisherID,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByTile(String title, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByTitle(title,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByDescription(String description, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByDescription(description,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsBySpec(Specification<Board> specification, Pageable pageable) {
        Page<Board> boards = this.boardDao.findAll(specification,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getSimilarBoards(UUID boardID, Pageable pageable) {;
        Board requiredBoard = this.boardDao.findById(boardID).orElseThrow();
        BoardSpecifications.Filter filter = new BoardSpecifications.Filter(requiredBoard);
        Page<Board> boards = this.boardDao.findAll(BoardSpecifications.withFilter(filter),pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public PagedModel<BoardDto> getBoardsByStatus(BoardStatus status, Pageable pageable) {
        Page<Board> boards = this.boardDao.getBoardsByStatus(status,pageable);
        return this.pagedResourcesAssembler.toModel(boards,modelAssembler);
    }

    @Override
    public CollectionModel<BoardVisibility> getVisibilities() {
        List<BoardVisibility> visibilities = Arrays.stream(BoardVisibility.values()).toList();
        return CollectionModel.of(visibilities);
    }

    @Override
    public CollectionModel<BoardStatus> getStatues() {
        return CollectionModel.of(Arrays.stream(BoardStatus.values()).toList());
    }

    @Override
    public CollectionModel<String> getOrderTypes() {
        List<String> orderTypes = SpecificationsUtils.generateOrderTypes(Board.class);
        return CollectionModel.of(orderTypes);
    }

    @Override
    public BoardDto getBoard(UUID id) {
        Board board = this.boardDao.findById(id).orElseThrow();
        return this.modelMapper.map(board,BoardDto.class);
    }

    @Override
    @Transactional
    public BoardDto createBoard(CreateBoardDto createBoardDto) {
        User publisher = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board board = new Board();
        board.setTitle(createBoardDto.getTitle());
        board.setDescription(createBoardDto.getDescription());
        board.setVisibility(createBoardDto.getVisibility());
        board.setMaxMembers(createBoardDto.getMaxMembers());
        board.setExpirationDate(createBoardDto.getExpirationDate());
        board.setStatus(BoardStatus.OPEN);
        board.setPublisher(publisher);
        board = this.boardDao.save(board);
        createDefaultGroup("TO DO",0,board,publisher);
        createDefaultGroup("IN PROGRESS",1,board,publisher);
        createDefaultGroup("COMPLETED",2,board,publisher);
        createDefaultTag(publisher,"TO DO","#FF0000",board);
        createDefaultTag(publisher,"IN PROGRESS","#FFA500",board);
        createDefaultTag(publisher,"COMPLETED","#008000",board);
        BoardMember boardMember = new BoardMember();
        boardMember.setUser(publisher);
        boardMember.setBoard(board);
        this.boardMemberDao.save(boardMember);
        Role adminRole = new Role();
        Role memberRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setPublisher(publisher);
        adminRole.setBoard(board);
        adminRole = this.roleDao.save(adminRole);
        memberRole.setName("MEMBER");
        memberRole.setPublisher(publisher);
        memberRole.setBoard(board);
        this.roleDao.save(memberRole);
        RoleOwner roleOwner = new RoleOwner();
        roleOwner.setRole(adminRole);
        roleOwner.setOwner(publisher);
        this.roleOwnerDao.save(roleOwner);
        return this.modelMapper.map(board,BoardDto.class);
    }

    private void createAdminPermissions(Role requiredRole) {
        Permission writeBoardPermission = new Permission();
        writeBoardPermission.setName("WRITE_BOARD");
        writeBoardPermission.setType(PermissionType.WRITE_BOARD);
        writeBoardPermission.setRole(requiredRole);
        writeBoardPermission = this.permissionDao.save(writeBoardPermission);
        Permission readBoardPermission = new Permission();
        readBoardPermission.setName("READ_BOARD");
        readBoardPermission.setType(PermissionType.READ_BOARD);
        readBoardPermission.setRole(requiredRole);
        readBoardPermission = this.permissionDao.save(readBoardPermission);
        Permission writeTasksPermission = new Permission();
        writeTasksPermission.setName("WRITE_TASKS");
        writeTasksPermission.setType(PermissionType.WRITE_TASKS);
        writeTasksPermission.setRole(requiredRole);
        writeTasksPermission = this.permissionDao.save(writeTasksPermission);
        Permission readTasksPermission = new Permission();
        readTasksPermission.setName("READ_TASKS");
        readTasksPermission.setType(PermissionType.READ_TASKS);
        readTasksPermission.setRole(requiredRole);
        readTasksPermission = this.permissionDao.save(readTasksPermission);
        Permission writeAssignedTaskPermission = new Permission();
        writeAssignedTaskPermission.setName("WRITE_ASSIGNED_TASK");
        writeAssignedTaskPermission.setType(PermissionType.WRITE_ASSIGNED_TASK);
        writeAssignedTaskPermission.setRole(requiredRole);
        writeAssignedTaskPermission = this.permissionDao.save(writeAssignedTaskPermission);
        Permission readAssignedTaskPermission = new Permission();
        readAssignedTaskPermission.setName("READ_ASSIGNED_TASK");
        readAssignedTaskPermission.setType(PermissionType.READ_ASSIGNED_TASK);
        readAssignedTaskPermission.setRole(requiredRole);
        readAssignedTaskPermission = this.permissionDao.save(readAssignedTaskPermission);
    }

    private void createDefaultTag(User publisher,String name,String color,Board board) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setColor(color);
        tag.setBoard(board);
        tag.setPublisher(publisher);
        this.tagDao.save(tag);
    }

    private void createMemberPermissions(Role requiredRole)
    {
        Permission writeAssignedTaskPermission = new Permission();
        writeAssignedTaskPermission.setName("WRITE_ASSIGNED_TASK");
        writeAssignedTaskPermission.setType(PermissionType.WRITE_ASSIGNED_TASK);
        writeAssignedTaskPermission.setRole(requiredRole);
        writeAssignedTaskPermission = this.permissionDao.save(writeAssignedTaskPermission);
        Permission readAssignedTaskPermission = new Permission();
        readAssignedTaskPermission.setName("READ_ASSIGNED_TASK");
        readAssignedTaskPermission.setType(PermissionType.READ_ASSIGNED_TASK);
        readAssignedTaskPermission.setRole(requiredRole);
        readAssignedTaskPermission = this.permissionDao.save(readAssignedTaskPermission);
        Permission readTasksPermission = new Permission();
        readTasksPermission.setType(PermissionType.READ_TASKS);
        readTasksPermission.setRole(requiredRole);
        readTasksPermission = this.permissionDao.save(readTasksPermission);
    }

    private void createDefaultGroup(String name, Integer order, Board board, User publisher) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName(name);
        taskGroup.setCurrentOrder(order);
        taskGroup.setStatus(TaskGroupStatus.OPEN);
        taskGroup.setBoard(board);
        taskGroup.setPublisher(publisher);
        this.taskGroupDao.save(taskGroup);
    }

    @Override
    @Transactional
    public BoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        Board board = this.boardDao.findById(updateBoardDto.getBoardID()).orElseThrow();
        if(updateBoardDto.getTitle() != null)
            board.setTitle(updateBoardDto.getTitle());
        if(updateBoardDto.getDescription() != null)
            board.setDescription(updateBoardDto.getDescription());
        if(updateBoardDto.getVisibility() != null)
            board.setVisibility(updateBoardDto.getVisibility());
        if(updateBoardDto.getMaxMembers() != null)
            board.setMaxMembers(updateBoardDto.getMaxMembers());
        if(updateBoardDto.getStatus() != null) {
            if(board.getStatus().equals(BoardStatus.CLOSED))
                throw new InvalidFormat("error.board.update.invalidOldStatus");
            if(updateBoardDto.getStatus().equals(BoardStatus.EXPIRED))
                throw new InvalidFormat("error.board.update.invalidNewStatus");
            board.setStatus(updateBoardDto.getStatus());
        }
        board = this.boardDao.save(board);
        return this.modelMapper.map(board,BoardDto.class);
    }

    @Override
    @Transactional
    public void handleExpiredBoards() {
        List<Board> boards = this.boardDao.getBoardsByDate(LocalDate.now());
        for(Board current : boards)
            current.setStatus(BoardStatus.EXPIRED);
        this.boardDao.saveAll(boards);
    }

    @Override
    @Transactional
    public void deleteExpiredBoards() {
        List<Board> boards = this.boardDao.getBoardsByStatus(BoardStatus.EXPIRED);
        this.boardDao.deleteAll(boards);
    }

    @Override
    @Transactional
    public void deleteBoard(UUID id) {
        this.boardDao.findById(id).orElseThrow();
        this.boardDao.deleteById(id);
    }
}
