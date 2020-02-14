package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.LieuMetaGroupe} entity.
 */
public class LieuMetaGroupeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nom;

    @Size(max = 100)
    private String adresse;

    @Size(max = 100)
    private String codePostal;

    @Size(max = 100)
    private String ville;

    private Double lat;

    private Double lon;

    @Lob
    private String detail;

    @Size(max = 10)
    private String departementGroupe;


    private Long groupeId;

    private String groupeNom;

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDepartementGroupe() {
        return departementGroupe;
    }

    public void setDepartementGroupe(String departementGroupe) {
        this.departementGroupe = departementGroupe;
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

        LieuMetaGroupeDTO lieuMetaGroupeDTO = (LieuMetaGroupeDTO) o;
        if (lieuMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lieuMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LieuMetaGroupeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", detail='" + getDetail() + "'" +
            ", departementGroupe='" + getDepartementGroupe() + "'" +
            ", groupeId=" + getGroupeId() +
            ", groupeNom='" + getGroupeNom() + "'" +
            "}";
    }
}
