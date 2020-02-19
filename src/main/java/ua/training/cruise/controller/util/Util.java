package ua.training.cruise.controller.util;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import ua.training.cruise.controller.SessionAttributeConstants;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.UnreachableRequest;

import javax.servlet.http.HttpSession;

import static ua.training.cruise.controller.SessionAttributeConstants.*;

@Log4j
@Component
public class Util {

    public static void createUpdateUserInSession(User user, HttpSession session) {
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
        log.debug("Session Order and Cruise cleared");
    }

    public static long calcOrderTotalPrice(HttpSession session) {
        Order order = getSessionOrder(session);
        return order.getTicket().getPriceWithDiscount() + order.getExcursions().stream()
                .mapToLong(Excursion::getPrice)
                .sum();
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER);
    }
}
