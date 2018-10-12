/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bcbpm.activiti.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcbpm.activiti.model.editor.UserRepresentation;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * REST controller for managing the current user's account.
 * 
 * @author Joram Barrez
 */
@RestController
@RequestMapping(value = "/app")
public class AccountResource{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionDeal sessionDeal = null;

    @RequestMapping(value = "/authentication", method = RequestMethod.GET, produces = { "application/json" })
    public void isAuthentication(HttpServletRequest request){
    }

    /**
     * GET  /rest/authenticate -> check if the user is authenticated, and return its full name.
     */
    @RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = { "application/json" })
    public ObjectNode isAuthenticated(HttpServletRequest request){
        User user = (User) sessionDeal.getNowUserFront();

        if(user == null){
            throw new BusinessException("请先登录系统");
        }

        ObjectNode result = objectMapper.createObjectNode();
        result.put("login", user.getUserName());
        return result;
    }

    /**
     * GET  /rest/account -> get the current user.
     */
    @RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = "application/json")
    public UserRepresentation getAccount(){
        User user = (User) sessionDeal.getNowUserFront();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(user.getUserId());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFullName(user.getUserName());
        //        GroupRepresentation gp = new GroupRepresentation();
        //        gp.setId("ROLE_ADMIN");
        //        gp.setName("BOSS");
        //        gp.setType("SECURITY");
        //        userRepresentation.getGroups().add(gp);
        //        for(Group group : groups){
        //            userRepresentation.getGroups().add(new GroupRepresentation(group));
        //        }

        return userRepresentation;
    }
}
