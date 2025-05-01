package com.planto.user_service.entity;

/**
 * @author Praful
 */
import jakarta.persistence.*;

@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_authorities_users"))
    @Column(name = "username")
    private String user;

    @Column(nullable = false, length = 50)
    private String authority;

    // Default constructor
    public Authorities() {
        this.authority = "USER"; // Default authority
    }

    // Parameterized constructor
    public Authorities(String user, String authority) {
        this.user = user;
        this.authority = authority;
    }

    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
