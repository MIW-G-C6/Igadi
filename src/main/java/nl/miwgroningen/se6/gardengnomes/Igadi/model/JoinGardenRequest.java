package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

@Entity
public class JoinGardenRequest {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="requestId")
    private Integer requestId;

    @Column(unique = false, nullable = false, name="status")
    private String status;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Garden garden;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
