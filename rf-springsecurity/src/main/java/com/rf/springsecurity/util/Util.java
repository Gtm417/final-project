package com.rf.springsecurity.util;

import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.entity.port.Excursion;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Component
public class Util {

    public void addListOfExcursionsToSession(HttpSession session){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute("excursionsDTO"));
        if(excursionsDTO == null){
            session.setAttribute("excursionsDTO", new ExcursionsDTO(new ArrayList<>()));
        }
    }

    public void addExcursionToListInSession(HttpSession session, Excursion excursion) throws Exception {
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute("excursionsDTO"));
        if(excursionsDTO.getExcursionsDTO().stream().anyMatch(excursion1 -> excursion1.equals(excursion))){
            throw new Exception();//TODO  norm Exception
        }
        excursionsDTO.getExcursionsDTO().add(excursion);
    }

    public void resetExcursionSession(HttpSession session){
       ((ExcursionsDTO)session.getAttribute("excursionsDTO")).getExcursionsDTO().clear();

    }


}
