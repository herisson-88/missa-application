package fr.herisson.missa.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.MembreMetaGroupe} entity.
 */
public class MembreMetaGroupeDTO implements Serializable {

    private Long id;

    private Boolean validated;

    private ZonedDateTime dateValidation;

    private Boolean admin;

    
    @Lob
    private String presentation;

    
    @Lob
    private String connaissance;

    @NotNull
    private Boolean mailingList;


    private Long groupeMembreId;

    private String groupeMembreNom;

    private Long valideParId;

    private Long missaUserId;

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

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getConnaissance() {
        return connaissance;
    }

    public void setConnaissance(String connaissance) {
        this.connaissance = connaissance;
    }

    public Boolean isMailingList() {
        return mailingList;
    }

    public void setMailingList(Boolean mailingList) {
        this.mailingList = mailingList;
    }

    public Long getGroupeMembreId() {
        return groupeMembreId;
    }

    public void setGroupeMembreId(Long metaGroupeId) {
        this.groupeMembreId = metaGroupeId;
    }

    public String getGroupeMembreNom() {
        return groupeMembreNom;
    }

    public void setGroupeMembreNom(String metaGroupeNom) {
        this.groupeMembreNom = metaGroupeNom;
    }

    public Long getValideParId() {
        return valideParId;
    }

    public void setValideParId(Long missaUserId) {
        this.valideParId = missaUserId;
    }

    public Long getMissaUserId() {
        return missaUserId;
    }

    public void setMissaUserId(Long missaUserId) {
        this.missaUserId = missaUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MembreMetaGroupeDTO membreMetaGroupeDTO = (MembreMetaGroupeDTO) o;
        if (membreMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreMetaGroupeDTO{" +
            "id=" + getId() +
            ", validated='" + isValidated() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", admin='" + isAdmin() + "'" +
            ", presentation='" + getPresentation() + "'" +
            ", connaissance='" + getConnaissance() + "'" +
            ", mailingList='" + isMailingList() + "'" +
            ", groupeMembreId=" + getGroupeMembreId() +
            ", groupeMembreNom='" + getGroupeMembreNom() + "'" +
            ", valideParId=" + getValideParId() +
            ", missaUserId=" + getMissaUserId() +
            "}";
    }
}
