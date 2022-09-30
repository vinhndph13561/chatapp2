package com.vmo.chatapp2.relationship.service;

import com.vmo.chatapp2.relationship.bean.RelationshipBean;
import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.relationship.form.RelationshipForm;
import com.vmo.chatapp2.relationship.form.UpdateRelaForm;
import com.vmo.chatapp2.utils.ICommonService;

public interface IRelationshipService extends ICommonService<RelationshipBO, RelationshipBean, RelationshipForm> {
    void saveOrUpdate(UpdateRelaForm entity, int status);
}
