package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.herisson.missa.domain.enumeration.EtatUser;

/**
 * A DTO for the {@link fr.herisson.missa.domain.MissaUser} entity.
 */
public class MissaUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String codePostal;

    @NotNull
    @Size(max = 20)
    private String initiales;

    @NotNull
    @Size(max = 50)
    private String nom;

    @NotNull
    @Size(max = 50)
    private String prenom;

    @NotNull
    @Size(max = 50)
    private String mail;

    private EtatUser etat;


    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getInitiales() {
        return initiales;
    }

    public void setInitiales(String initiales) {
        this.initiales = initiales;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EtatUser getEtat() {
        return etat;
    }

    public void setEtat(EtatUser etat) {
        this.etat = etat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MissaUserDTO missaUserDTO = (MissaUserDTO) o;
        if (missaUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), missaUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MissaUserDTO{" +
            "id=" + getId() +
            ", codePostal='" + getCodePostal() + "'" +
            ", initiales='" + getInitiales() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            ", etat='" + getEtat() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
