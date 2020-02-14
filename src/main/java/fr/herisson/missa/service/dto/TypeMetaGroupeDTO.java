package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.TypeMetaGroupe} entity.
 */
public class TypeMetaGroupeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String typeDuGroupe;

    @Size(max = 200)
    private String iconeFa;

    @Lob
    private String detail;

    private Integer ordreMail;


    private Long reseauId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeDuGroupe() {
        return typeDuGroupe;
    }

    public void setTypeDuGroupe(String typeDuGroupe) {
        this.typeDuGroupe = typeDuGroupe;
    }

    public String getIconeFa() {
        return iconeFa;
    }

    public void setIconeFa(String iconeFa) {
        this.iconeFa = iconeFa;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getOrdreMail() {
        return ordreMail;
    }

    public void setOrdreMail(Integer ordreMail) {
        this.ordreMail = ordreMail;
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

        TypeMetaGroupeDTO typeMetaGroupeDTO = (TypeMetaGroupeDTO) o;
        if (typeMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeMetaGroupeDTO{" +
            "id=" + getId() +
            ", typeDuGroupe='" + getTypeDuGroupe() + "'" +
            ", iconeFa='" + getIconeFa() + "'" +
            ", detail='" + getDetail() + "'" +
            ", ordreMail=" + getOrdreMail() +
            ", reseauId=" + getReseauId() +
            "}";
    }
}
