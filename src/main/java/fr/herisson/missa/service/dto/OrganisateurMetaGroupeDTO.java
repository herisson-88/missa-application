package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.OrganisateurMetaGroupe} entity.
 */
public class OrganisateurMetaGroupeDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String site;

    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String prenom;

    @Size(max = 20)
    private String telephone;

    @Size(max = 100)
    private String mail;

    @Size(max = 100)
    private String adresse;

    @Size(max = 20)
    private String codePostal;

    @Size(max = 100)
    private String ville;

    @Lob
    private String detail;


    private Long groupeId;

    private String groupeNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long metaGroupeId) {
        this.groupeId = metaGroupeId;
    }

    public String getGroupeNom() {
        return groupeNom;
    }

    public void setGroupeNom(String metaGroupeNom) {
        this.groupeNom = metaGroupeNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO = (OrganisateurMetaGroupeDTO) o;
        if (organisateurMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisateurMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganisateurMetaGroupeDTO{" +
            "id=" + getId() +
            ", site='" + getSite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mail='" + getMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", detail='" + getDetail() + "'" +
            ", groupeId=" + getGroupeId() +
            ", groupeNom='" + getGroupeNom() + "'" +
            "}";
    }
}
