package com.vmo.chatapp2.relationship.service.imp;

import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.relationship.dao.RelationshipDao;
import com.vmo.chatapp2.relationship.form.RelationshipForm;
import com.vmo.chatapp2.relationship.service.IRelationshipService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImp implements IRelationshipService {
    private RelationshipDao relationshipDao;

    @Override
    public List<RelationshipBO> findAll() {
        return relationshipDao.findAll();
    }

    @Override
    public RelationshipBO findById(Long id) {
        Optional<RelationshipBO> findRelationshipBO = relationshipDao.findById(id);
        if (findRelationshipBO.isPresent()){
            return findRelationshipBO.get();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(RelationshipForm entity) {
        return false;
    }

    @Override
    public void saveOrUpdate(RelationshipForm entity, int status) {
        RelationshipBO relationshipBO = new RelationshipBO();
        Date date = new Date();
        RelationshipBO findAccount = relationshipDao.findById(entity.getId()).map(relationship -> {
            relationship.setStatus(status);
            if (!entity.getNote().isEmpty()){
                relationship.setNote(entity.getNote());
            }
            return relationshipDao.save(relationship);
        }).orElseGet(() ->{
            relationshipBO.setSender(entity.getSender());
            relationshipBO.setReceiver(entity.getReceiver());
            relationshipBO.setStatus(status);
            relationshipBO.setNote(entity.getNote());
            return relationshipDao.save(relationshipBO);
        });
    }
}
