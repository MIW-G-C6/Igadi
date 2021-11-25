package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Annemarleen Bosma <makeItWokr2021@annemarleenbosma.nl>
 * Dit is wat het programma doet:
 */

@Component
public class UserHelper {

    public boolean IsUserNameDuplicate(Exception ex) {
        return ex instanceof DataIntegrityViolationException &&
                ex.getCause() instanceof ConstraintViolationException &&
                ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException &&
                ex.getCause().getCause().getMessage().contains("Duplicate entry");
    }
}

