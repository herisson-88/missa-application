package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.herisson.missa.domain.Connaissance} entity.
 */
public class ConnaissanceDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String prenom;

    @Size(max = 100)
    private String mail;

    @Size(max = 200)
    private String idFaceBook;

    private Boolean parraine;


    private Long connuParId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIdFaceBook() {
        return idFaceBook;
    }

    public void setIdFaceBook(String idFaceBook) {
        this.idFaceBook = idFaceBook;
    }

    public Boolean isParraine() {
        return parraine;
    }

    public void setParraine(Boolean parraine) {
        this.parraine = parraine;
    }

    public Long getConnuParId() {
        return connuParId;
    }

    public void setConnuParId(Long missaUserId) {
        this.connuParId = missaUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConnaissanceDTO connaissanceDTO = (ConnaissanceDTO) o;
        if (connaissanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connaissanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConnaissanceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            ", idFaceBook='" + getIdFaceBook() + "'" +
            ", parraine='" + isParraine() + "'" +
            ", connuParId=" + getConnuParId() +
            "}";
    }
}
