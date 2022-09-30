package com.vmo.chatapp2.relationship.service.imp;

import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.relationship.dao.RelationshipDao;
import com.vmo.chatapp2.relationship.form.RelationshipForm;
import com.vmo.chatapp2.relationship.form.UpdateRelaForm;
import com.vmo.chatapp2.relationship.service.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImp implements IRelationshipService {
    @Autowired
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
        RelationshipBO rBo = new RelationshipBO();
        rBo.setSender(entity.getSender());
        rBo.setReceiver(entity.getReceiver());
        rBo.setNote(entity.getNote());
        rBo.setStatus(0);
        relationshipDao.save(rBo);
        return true;
    }

    @Override
    public void saveOrUpdate(UpdateRelaForm entity, int status) {
        Optional<RelationshipBO> relationshipBO = relationshipDao.findById(entity.getId());
        relationshipBO.get().setStatus(status);
        if (!entity.getNote().isEmpty()){
            relationshipBO.get().setNote(entity.getNote());
        }
        relationshipDao.save(relationshipBO.get());
    }
}
