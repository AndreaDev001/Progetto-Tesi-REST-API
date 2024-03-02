package com.progettotirocinio.restapi.services.implementations.images;


import com.progettotirocinio.restapi.config.ImageUtils;
import com.progettotirocinio.restapi.config.caching.CacheHandler;
import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.BoardDao;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.BoardImageDao;
import com.progettotirocinio.restapi.data.dto.input.create.images.CreateBoardImageDto;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.User;
import com.progettotirocinio.restapi.data.entities.enums.ImageOwnerType;
import com.progettotirocinio.restapi.data.entities.enums.ImageType;
import com.progettotirocinio.restapi.data.entities.images.BoardImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.BoardImageService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BoardImageServiceImp extends GenericServiceImp<BoardImage, BoardImageDto> implements BoardImageService
{
    private final BoardDao boardDao;
    private final BoardImageDao boardImageDao;

    public BoardImageServiceImp(CacheHandler cacheHandler,BoardDao boardDao, UserDao userDao, Mapper mapper, BoardImageDao boardImageDao, PagedResourcesAssembler<BoardImage> pagedResourcesAssembler) {
        super(cacheHandler,userDao,mapper, BoardImage.class,BoardImageDto.class, pagedResourcesAssembler);
        this.boardImageDao = boardImageDao;
        this.boardDao = boardDao;
    }

    @Override
    public PagedModel<BoardImageDto> getBoardImages(Pageable pageable) {
        Page<BoardImage> boardImages = this.boardImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boardImages,modelAssembler);
    }

    @Override
    public BoardImageDto getBoardImageByBoard(UUID boardID) {
        BoardImage boardImage = this.boardImageDao.getBoardImageByBoard(boardID).orElseThrow();
        return this.modelMapper.map(boardImage,BoardImageDto.class);
    }


    @Override
    public BoardImageDto getBoard(UUID imageID) {
        BoardImage boardImage = this.boardImageDao.findById(imageID).orElseThrow();
        return this.modelMapper.map(boardImage,BoardImageDto.class);
    }

    @Override
    @Transactional
    @SneakyThrows
    public BoardImageDto uploadImage(CreateBoardImageDto createBoardImageDto) {
        User authenticatedUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Board requiredBoard  = this.boardDao.findById(createBoardImageDto.getBoardID()).orElseThrow();
        Optional<BoardImage> boardImageOptional = this.boardImageDao.getBoardImageByBoard(createBoardImageDto.getBoardID());
        BoardImage boardImage = boardImageOptional.orElseGet(BoardImage::new);
        boardImage.setBoard(requiredBoard);
        boardImage.setType(ImageUtils.getImageType(createBoardImageDto.getFile().getContentType()));
        boardImage.setOwner(ImageOwnerType.BOARD);
        boardImage.setImage(createBoardImageDto.getFile().getBytes());
        boardImage.setUploader(authenticatedUser);
        boardImage = this.boardImageDao.save(boardImage);
        return this.modelMapper.map(boardImage,BoardImageDto.class);
    }
}
