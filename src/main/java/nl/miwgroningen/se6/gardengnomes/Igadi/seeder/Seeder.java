package nl.miwgroningen.se6.gardengnomes.Igadi.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Component
public class Seeder {

    @Autowired
    public Seeder() {

    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        //hier seeding methods
    }
}
