package fr.herisson.missa.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import fr.herisson.missa.domain.enumeration.MembreDiffusion;
import fr.herisson.missa.domain.enumeration.Visibilite;

/**
 * A DTO for the {@link fr.herisson.missa.domain.MetaGroupe} entity.
 */
public class MetaGroupeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nom;

    private Boolean autoriteValidation;

    private MembreDiffusion membreParent;

    private Visibilite visibilite;

    private Boolean droitEnvoiDeMail;

    private Boolean demandeDiffusionVerticale;

    private Boolean messagerieModeree;

    private Boolean groupeFinal;

    private ZonedDateTime dateValidation;

    @Lob
    private String detail;


    private Long parentId;

    private String parentNom;

    private Long typeId;

    private Long reseauId;

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

    public Boolean isAutoriteValidation() {
        return autoriteValidation;
    }

    public void setAutoriteValidation(Boolean autoriteValidation) {
        this.autoriteValidation = autoriteValidation;
    }

    public MembreDiffusion getMembreParent() {
        return membreParent;
    }

    public void setMembreParent(MembreDiffusion membreParent) {
        this.membreParent = membreParent;
    }

    public Visibilite getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(Visibilite visibilite) {
        this.visibilite = visibilite;
    }

    public Boolean isDroitEnvoiDeMail() {
        return droitEnvoiDeMail;
    }

    public void setDroitEnvoiDeMail(Boolean droitEnvoiDeMail) {
        this.droitEnvoiDeMail = droitEnvoiDeMail;
    }

    public Boolean isDemandeDiffusionVerticale() {
        return demandeDiffusionVerticale;
    }

    public void setDemandeDiffusionVerticale(Boolean demandeDiffusionVerticale) {
        this.demandeDiffusionVerticale = demandeDiffusionVerticale;
    }

    public Boolean isMessagerieModeree() {
        return messagerieModeree;
    }

    public void setMessagerieModeree(Boolean messagerieModeree) {
        this.messagerieModeree = messagerieModeree;
    }

    public Boolean isGroupeFinal() {
        return groupeFinal;
    }

    public void setGroupeFinal(Boolean groupeFinal) {
        this.groupeFinal = groupeFinal;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long metaGroupeId) {
        this.parentId = metaGroupeId;
    }

    public String getParentNom() {
        return parentNom;
    }

    public void setParentNom(String metaGroupeNom) {
        this.parentNom = metaGroupeNom;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeMetaGroupeId) {
        this.typeId = typeMetaGroupeId;
    }

    public Long getReseauId() {
        return reseauId;
    }

    public void setReseauId(Long reseauId) {
        this.reseauId = reseauId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetaGroupeDTO metaGroupeDTO = (MetaGroupeDTO) o;
        if (metaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetaGroupeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", autoriteValidation='" + isAutoriteValidation() + "'" +
            ", membreParent='" + getMembreParent() + "'" +
            ", visibilite='" + getVisibilite() + "'" +
            ", droitEnvoiDeMail='" + isDroitEnvoiDeMail() + "'" +
            ", demandeDiffusionVerticale='" + isDemandeDiffusionVerticale() + "'" +
            ", messagerieModeree='" + isMessagerieModeree() + "'" +
            ", groupeFinal='" + isGroupeFinal() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", detail='" + getDetail() + "'" +
            ", parentId=" + getParentId() +
            ", parentNom='" + getParentNom() + "'" +
            ", typeId=" + getTypeId() +
            ", reseauId=" + getReseauId() +
            "}";
    }
}
