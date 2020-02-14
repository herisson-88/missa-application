package fr.herisson.missa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import fr.herisson.missa.domain.enumeration.TypeDoc;

/**
 * A DTO for the {@link fr.herisson.missa.domain.LienDocMetaGroupe} entity.
 */
public class LienDocMetaGroupeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nom;

    @NotNull
    private TypeDoc typeDuDoc;

    
    @Lob
    private byte[] icone;

    private String iconeContentType;
    
    @Lob
    private byte[] image;

    private String imageContentType;
    
    @Lob
    private byte[] doc;

    private String docContentType;
    @Lob
    private String detail;


    private Long groupeId;

    private String groupeNom;

    private Long typeId;

    private String typeTypeDuGroupe;

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

    public TypeDoc getTypeDuDoc() {
        return typeDuDoc;
    }

    public void setTypeDuDoc(TypeDoc typeDuDoc) {
        this.typeDuDoc = typeDuDoc;
    }

    public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }

    public String getIconeContentType() {
        return iconeContentType;
    }

    public void setIconeContentType(String iconeContentType) {
        this.iconeContentType = iconeContentType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getDoc() {
        return doc;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeMetaGroupeId) {
        this.typeId = typeMetaGroupeId;
    }

    public String getTypeTypeDuGroupe() {
        return typeTypeDuGroupe;
    }

    public void setTypeTypeDuGroupe(String typeMetaGroupeTypeDuGroupe) {
        this.typeTypeDuGroupe = typeMetaGroupeTypeDuGroupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = (LienDocMetaGroupeDTO) o;
        if (lienDocMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lienDocMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LienDocMetaGroupeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", typeDuDoc='" + getTypeDuDoc() + "'" +
            ", icone='" + getIcone() + "'" +
            ", image='" + getImage() + "'" +
            ", doc='" + getDoc() + "'" +
            ", detail='" + getDetail() + "'" +
            ", groupeId=" + getGroupeId() +
            ", groupeNom='" + getGroupeNom() + "'" +
            ", typeId=" + getTypeId() +
            ", typeTypeDuGroupe='" + getTypeTypeDuGroupe() + "'" +
            "}";
    }
}
