package com.progettotirocinio.restapi.services.implementations.images;


import com.progettotirocinio.restapi.config.mapper.Mapper;
import com.progettotirocinio.restapi.data.dao.UserDao;
import com.progettotirocinio.restapi.data.dao.images.BoardImageDao;
import com.progettotirocinio.restapi.data.dto.output.images.BoardImageDto;
import com.progettotirocinio.restapi.data.entities.Board;
import com.progettotirocinio.restapi.data.entities.images.BoardImage;
import com.progettotirocinio.restapi.services.implementations.GenericServiceImp;
import com.progettotirocinio.restapi.services.interfaces.images.BoardImageService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BoardImageServiceImp extends GenericServiceImp<BoardImage, BoardImageDto> implements BoardImageService
{
    private final BoardImageDao boardImageDao;

    public BoardImageServiceImp(UserDao userDao,Mapper mapper, BoardImageDao boardImageDao, PagedResourcesAssembler<BoardImage> pagedResourcesAssembler) {
        super(userDao,mapper, BoardImage.class,BoardImageDto.class, pagedResourcesAssembler);
        this.boardImageDao = boardImageDao;
    }

    @Override
    public PagedModel<BoardImageDto> getBoardImages(Pageable pageable) {
        Page<BoardImage> boardImages = this.boardImageDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(boardImages,modelAssembler);
    }

    @Override
    public BoardImageDto getBoardImageByBoard(UUID boardID) {
        BoardImage boardImage = this.boardImageDao.getBoard(boardID).orElseThrow();
        return this.modelMapper.map(boardImage,BoardImageDto.class);
    }


    @Override
    public BoardImageDto getBoard(UUID boardID) {
        return null;
    }
}
