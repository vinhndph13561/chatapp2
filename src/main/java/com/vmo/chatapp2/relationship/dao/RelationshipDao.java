package com.vmo.chatapp2.relationship.dao;

import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipDao extends JpaRepository<RelationshipBO, Long> {
    @Query("select r from RelationshipBO r where r.receiver.id=?1 and r.status=1")
    List<RelationshipBO> findByReceiver(Long id);

    @Query("select r from RelationshipBO r where r.status=0")
    List<RelationshipBO> findAllRequest();
}
