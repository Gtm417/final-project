package ua.training.cruise.controller.util;

import org.springframework.stereotype.Component;
import ua.training.cruise.controller.SessionAttributeConstants;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.UnreachableRequest;

import javax.servlet.http.HttpSession;

import static ua.training.cruise.controller.SessionAttributeConstants.*;

@Component
public class Util {

    public static void createUpdateUserToSession(User user, HttpSession session) {
        session.setAttribute(SESSION_USER, user);
    }

    public static Order getSessionOrder(HttpSession session) {
        if (session.getAttribute(SESSION_ORDER) == null) {
            throw new UnreachableRequest("There is no Order in Session");
        }
        return (Order) session.getAttribute(SESSION_ORDER);
    }

    public static Cruise getSessionCruise(HttpSession session) {
        if (session.getAttribute(SESSION_CRUISE) == null) {
            throw new UnreachableRequest("There is no Cruise in Session");
        }
        return (Cruise) session.getAttribute(SESSION_CRUISE);
    }

    public static void clearBuySessionAttributes(HttpSession session) {
        session.removeAttribute(SessionAttributeConstants.SESSION_ORDER);
        session.removeAttribute(SessionAttributeConstants.SESSION_CRUISE);
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER);
    }

}
