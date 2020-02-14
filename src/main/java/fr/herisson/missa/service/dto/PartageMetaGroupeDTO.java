package fr.herisson.missa.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.PartageMetaGroupe} entity.
 */
public class PartageMetaGroupeDTO implements Serializable {

    private Long id;

    private Boolean validated;

    private ZonedDateTime dateValidation;

    @Lob
    private String detail;


    private Long metaGroupePartageId;

    private String metaGroupePartageNom;

    private Long metaGroupeDestinatairesId;

    private String metaGroupeDestinatairesNom;

    private Long auteurPartageId;

    private Long validateurDuPartageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getMetaGroupePartageId() {
        return metaGroupePartageId;
    }

    public void setMetaGroupePartageId(Long metaGroupeId) {
        this.metaGroupePartageId = metaGroupeId;
    }

    public String getMetaGroupePartageNom() {
        return metaGroupePartageNom;
    }

    public void setMetaGroupePartageNom(String metaGroupeNom) {
        this.metaGroupePartageNom = metaGroupeNom;
    }

    public Long getMetaGroupeDestinatairesId() {
        return metaGroupeDestinatairesId;
    }

    public void setMetaGroupeDestinatairesId(Long metaGroupeId) {
        this.metaGroupeDestinatairesId = metaGroupeId;
    }

    public String getMetaGroupeDestinatairesNom() {
        return metaGroupeDestinatairesNom;
    }

    public void setMetaGroupeDestinatairesNom(String metaGroupeNom) {
        this.metaGroupeDestinatairesNom = metaGroupeNom;
    }

    public Long getAuteurPartageId() {
        return auteurPartageId;
    }

    public void setAuteurPartageId(Long missaUserId) {
        this.auteurPartageId = missaUserId;
    }

    public Long getValidateurDuPartageId() {
        return validateurDuPartageId;
    }

    public void setValidateurDuPartageId(Long missaUserId) {
        this.validateurDuPartageId = missaUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartageMetaGroupeDTO partageMetaGroupeDTO = (PartageMetaGroupeDTO) o;
        if (partageMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partageMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartageMetaGroupeDTO{" +
            "id=" + getId() +
            ", validated='" + isValidated() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", detail='" + getDetail() + "'" +
            ", metaGroupePartageId=" + getMetaGroupePartageId() +
            ", metaGroupePartageNom='" + getMetaGroupePartageNom() + "'" +
            ", metaGroupeDestinatairesId=" + getMetaGroupeDestinatairesId() +
            ", metaGroupeDestinatairesNom='" + getMetaGroupeDestinatairesNom() + "'" +
            ", auteurPartageId=" + getAuteurPartageId() +
            ", validateurDuPartageId=" + getValidateurDuPartageId() +
            "}";
    }
}
