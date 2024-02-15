package com.progettotirocinio.restapi.data.dao.checklist;

import com.progettotirocinio.restapi.data.entities.CheckList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface CheckListDao extends JpaRepository<CheckList, UUID>
{
    @Query("select c from CheckList c where c.group.id = :requiredGroupID")
    List<CheckList> getCheckListsByGroup(@Param("requiredGroupID") UUID groupID);
    @Query("select c from CheckList c where c.publisher.id = :requiredUserID")
    Page<CheckList> getCheckListsByPublisher(@Param("requiredUserID") UUID publisherID,Pageable pageable);
    @Query("select c from CheckList c where c.name = :requiredName")
    Page<CheckList> getCheckListsByName(@Param("requiredName") String name, Pageable pageable);
    @Query("select c from CheckList c where c.name = :requiredName and c.group.id = :requiredGroupID")
    Optional<CheckList> getCheckListByNameAndGroup(@Param("requiredName") String name,@Param("requiredGroupID") UUID groupID);
}
