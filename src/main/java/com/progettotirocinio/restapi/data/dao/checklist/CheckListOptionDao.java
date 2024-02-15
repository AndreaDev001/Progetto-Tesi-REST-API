package com.progettotirocinio.restapi.data.dao.checklist;

import com.progettotirocinio.restapi.data.entities.CheckList;
import com.progettotirocinio.restapi.data.entities.CheckListOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;
import java.util.List;

@Repository
public interface CheckListOptionDao extends JpaRepository<CheckListOption, UUID>
{
    @Query("select c from CheckListOption c where c.checkList.id = :requiredID")
    List<CheckListOption> getOptionByCheckList(@Param("requiredID") UUID checkListID);
    @Query("select c from CheckListOption c where c.name = :requiredName")
    Page<CheckListOption> getOptionsByName(@Param("requiredName") String name, Pageable pageable);
    @Query("select c from CheckListOption c where c.publisher = :requiredPublisherID")
    Page<CheckListOption> getOptionsByPublisher(@Param("requiredPublisherID") UUID publisherID,Pageable pageable);
    @Query("select c from CheckListOption c where c.completed = :requiredCompleted")
    Page<CheckListOption> getOptionsByCompleted(@Param("requiredCompleted") boolean completed, Pageable pageable);
    @Query("select c from CheckListOption c where c.completed = :requiredCompleted and c.checkList.id = :requiredCheckListID")
    List<CheckListOption> getOptionsByCompletedAndCheckList(@Param("requiredCompleted") boolean completed,@Param("requiredCheckListID") UUID checkListID);
}
