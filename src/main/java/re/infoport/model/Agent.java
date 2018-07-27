package re.infoport.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agent {

    @JsonIgnore
    private List<Agent> agents;
    @JsonIgnore
    private String numeroPlanteur;
    @JsonIgnore
    private String nomResponsable;
    @JsonIgnore
    private String nomSocial;


    private Long id;

    private int roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    private String nom;
    private String prenom;
    private String username;
    private String telephone;
    private String email;

    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return nom+" "+prenom;
    }

    public Map<String, Object> getJasperParams() {


        Map<String, Object> m1 = new HashMap<String, Object>();

        return m1;
    }
}
