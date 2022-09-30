package com.vmo.chatapp2.relationship.controller;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.relationship.dao.RelationshipDao;
import com.vmo.chatapp2.relationship.form.RelationshipForm;
import com.vmo.chatapp2.relationship.form.UpdateRelaForm;
import com.vmo.chatapp2.relationship.service.imp.RelationshipServiceImp;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/relationship")
public class RelationshipController {
    @Autowired
    private RelationshipServiceImp relationshipServiceImp;

    @Autowired
    private RelationshipDao relationshipDao;

    @GetMapping("")
    @ResponseBody
    public List<RelationshipBO> findAll(){
        return relationshipServiceImp.findAll();
    }

    @GetMapping("/request")
    @ResponseBody
    public List<RelationshipBO> findAllRequest(){
        return relationshipDao.findAllRequest();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RelationshipBO findById(@PathVariable Long id){
        return relationshipServiceImp.findById(id);
    }

    @GetMapping("/friendrequest/{id}")
    @ResponseBody
    public List<RelationshipBO> findFriendRequest(@PathVariable Long id){
        return relationshipDao.findByReceiver(id);
    }

    @GetMapping("/friend/{id}")
    @ResponseBody
    public List<AccountBO> findFriend(@PathVariable Long id){
        List<RelationshipBO> list = relationshipDao.findFriend(id);
        List<AccountBO> accountBeanList = new ArrayList<>();
        System.out.println("list friend"+list);
        for (RelationshipBO relationshipBO: list){
            if (relationshipBO.getSender().getId()==id){
                accountBeanList.add(relationshipBO.getReceiver());
            }else {
                accountBeanList.add(relationshipBO.getSender());
            }
        }
        System.out.println("list account"+accountBeanList);
        return accountBeanList;
    }

    @PostMapping("")
    @ResponseBody
    public CommonResponse createRelationship(@Valid @RequestBody RelationshipForm form){
        relationshipServiceImp.saveOrUpdate(form);
        return new CommonResponse(1, "Sent! Wait for your friend's answer!");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public CommonResponse updateRelationship(@Valid @RequestBody UpdateRelaForm form, @PathVariable Long status){
        if (status==1) {
            relationshipServiceImp.saveOrUpdate(form, 1);
            return new CommonResponse(1, "You have accepted!");
        }
        if (status==2) {
            relationshipServiceImp.saveOrUpdate(form, 2);
            return new CommonResponse(1, "You have denied!");
        }
        if (status==3) {
            relationshipServiceImp.saveOrUpdate(form, 3);
            return new CommonResponse(1, "Unfriend successfully!");
        }
        return new CommonResponse(0, "Failed!");
    }
}
