package nl.miwgroningen.se6.gardengnomes.Igadi.helpers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

@Component
public class GardenHelper {

    public boolean IsGardenNameDuplicate(Exception ex) {
        return ex instanceof DataIntegrityViolationException &&
                ex.getCause() instanceof ConstraintViolationException &&
                ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException &&
                ex.getCause().getCause().getMessage().contains("Duplicate entry");
    }
}