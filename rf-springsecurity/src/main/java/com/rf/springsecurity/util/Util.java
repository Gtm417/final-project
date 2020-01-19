package com.rf.springsecurity.util;

import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static com.rf.springsecurity.controller.SessionAttributeConstants.SESSION_EXCURSIONS;
import static com.rf.springsecurity.controller.SessionAttributeConstants.SESSION_USER;

@Component
public class Util {

    public void addUserToSession(User user, HttpSession session){
        session.setAttribute(SESSION_USER, user);
    }

    public void addListOfExcursionsToSession(HttpSession session){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        if(excursionsDTO == null){
            session.setAttribute(SESSION_EXCURSIONS, new ExcursionsDTO(new ArrayList<>()));
        }
    }

    public void addExcursionToListInSession(HttpSession session, Excursion excursion) throws Exception {

        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        if(excursionsDTO.getExcursionsDTO().stream().anyMatch(excursion1 -> excursion1.equals(excursion))){
            throw new Exception();//TODO  Должно быть сделано через СЕТ  тогда не нужна эта ошибка
        }
        excursionsDTO.getExcursionsDTO().add(excursion);
    }

    public void resetExcursionSession(HttpSession session){
       ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS)).getExcursionsDTO().clear();
    }


}
