package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.MessageMetaGroupe} entity.
 */
public class MessageMetaGroupeDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String titre;

    private Boolean publique;

    
    @Lob
    private String message;


    private Long auteurId;

    private Long groupeId;

    private String groupeNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Boolean isPublique() {
        return publique;
    }

    public void setPublique(Boolean publique) {
        this.publique = publique;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(Long missaUserId) {
        this.auteurId = missaUserId;
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

        MessageMetaGroupeDTO messageMetaGroupeDTO = (MessageMetaGroupeDTO) o;
        if (messageMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageMetaGroupeDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", publique='" + isPublique() + "'" +
            ", message='" + getMessage() + "'" +
            ", auteurId=" + getAuteurId() +
            ", groupeId=" + getGroupeId() +
            ", groupeNom='" + getGroupeNom() + "'" +
            "}";
    }
}
