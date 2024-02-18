package com.progettotirocinio.restapi.services.implementations;

import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.caching.RequiresCaching;
import com.progettotirocinio.restapi.config.exceptions.InvalidFormat;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.TaskGroupDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.specifications.BoardSpecifications;
import com.progettotirocinio.restapi.data.dao.specifications.SpecificationsUtils;
import com.progettotirocinio.restapi.data.dto.input.create.CreateBoardDto;
import com.progettotirocinio.restapi.data.dto.input.update.UpdateBoardDto;
import com.progettotirocinio.restapi.data.dto.output.BoardDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.TaskGroup;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.BoardStatus;
import com.progettotirocinio.restapi.data.entities.enums.BoardVisibility;
import com.progettotirocinio.restapi.data.entities.enums.TaskGroupStatus;
import com.progettotirocinio.restapi.services.interfaces.BoardService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiresCaching(allCacheName = "ALL_BOARDS",allSearchName = "SEARCH_BOARDS",searchCachingRequired = true)
public class BoardServiceImp extends GenericServiceImp<Board, BoardDto> implements BoardService  {

    private final BoardDao boardDao;
    private final TaskGroupDao taskGroupDao;

    public BoardServiceImp(TaskGroupDao taskGroupDao,CacheHandler cacheHandler,UserDao userDao, BoardDao boardDao, Mapper mapper, PagedResourcesAssembler<Board> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper,Board.class,BoardDto.class,pagedResourcesAssembler);
        this.boardDao = boardDao;
        this.taskGroupDao = taskGroupDao;
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
        TaskGroup firstGroup = createDefaultGroup("TO DO",0,board,publisher);
        TaskGroup secondGroup = createDefaultGroup("IN PROGRESS",1,board,publisher);
        TaskGroup thirdGroup = createDefaultGroup("COMPLETED",2,board,publisher);
        board = this.boardDao.save(board);
        this.taskGroupDao.save(firstGroup);
        this.taskGroupDao.save(secondGroup);
        this.taskGroupDao.save(thirdGroup);
        return this.modelMapper.map(board,BoardDto.class);
    }

    private TaskGroup createDefaultGroup(String name,Integer order,Board board,User publisher) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName(name);
        taskGroup.setCurrentOrder(order);
        taskGroup.setStatus(TaskGroupStatus.OPEN);
        taskGroup.setBoard(board);
        taskGroup.setPublisher(publisher);
        return taskGroup;
    }

    @Override
    @Transactional
    public BoardDto updateBoard(UpdateBoardDto updateBoardDto) {
        Board board = this.boardDao.findById(updateBoardDto.getBoardID()).orElseThrow();
        if(board.getTitle() != null)
            board.setTitle(updateBoardDto.getTitle());
        if(board.getDescription() != null)
            board.setDescription(updateBoardDto.getDescription());
        if(board.getVisibility() != null)
            board.setVisibility(updateBoardDto.getVisibility());
        if(board.getMaxMembers() != null)
            board.setMaxMembers(updateBoardDto.getMaxMembers());
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
