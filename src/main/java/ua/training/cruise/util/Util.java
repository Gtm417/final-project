package ua.training.cruise.util;

import ua.training.cruise.dto.ExcursionsDTO;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.entity.user.User;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpSession;
import java.util.*;

import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_EXCURSIONS;
import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_USER;

@Component
public class Util {

    public void addUserToSession(User user, HttpSession session){
        session.setAttribute(SESSION_USER, user);
    }

    public void addSetOfExcursionsToSession(HttpSession session){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        if(excursionsDTO == null){
            session.setAttribute(SESSION_EXCURSIONS, new ExcursionsDTO(new TreeSet<>((exc1,exc2) -> (int)(exc1.getId() - exc2.getId()))));
        }
    }

    public void addExcursionToListInSession(HttpSession session, Excursion excursion){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        excursionsDTO.getExcursionsDTO().add(excursion);
    }

    public void removeExcursionFromSession(HttpSession session, Excursion excursion){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        excursionsDTO.getExcursionsDTO().remove(excursion);
    }

    public void resetExcursionSession(HttpSession session){
       ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS)).getExcursionsDTO().clear();
    }

    public long getTotalPriceSelectedExcursions(HttpSession session){
        ExcursionsDTO excursionsDTO = ((ExcursionsDTO)session.getAttribute(SESSION_EXCURSIONS));
        return Objects.nonNull(excursionsDTO) ?
                excursionsDTO.getExcursionsDTO().stream().mapToLong(Excursion::getPrice).sum()
                : 0;
    }

}
