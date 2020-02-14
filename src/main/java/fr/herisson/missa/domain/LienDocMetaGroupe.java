package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import fr.herisson.missa.domain.enumeration.TypeDoc;

/**
 * A LienDocMetaGroupe.
 */
@Entity
@Table(name = "lien_doc_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "liendocmetagroupe")
public class LienDocMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_du_doc", nullable = false)
    private TypeDoc typeDuDoc;

    
    @Lob
    @Column(name = "icone")
    private byte[] icone;

    @Column(name = "icone_content_type")
    private String iconeContentType;

    
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    
    @Lob
    @Column(name = "doc")
    private byte[] doc;

    @Column(name = "doc_content_type")
    private String docContentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JsonIgnoreProperties("docs")
    private MetaGroupe groupe;

    @ManyToOne
    @JsonIgnoreProperties("defaultDocs")
    private TypeMetaGroupe type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public LienDocMetaGroupe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeDoc getTypeDuDoc() {
        return typeDuDoc;
    }

    public LienDocMetaGroupe typeDuDoc(TypeDoc typeDuDoc) {
        this.typeDuDoc = typeDuDoc;
        return this;
    }

    public void setTypeDuDoc(TypeDoc typeDuDoc) {
        this.typeDuDoc = typeDuDoc;
    }

    public byte[] getIcone() {
        return icone;
    }

    public LienDocMetaGroupe icone(byte[] icone) {
        this.icone = icone;
        return this;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }

    public String getIconeContentType() {
        return iconeContentType;
    }

    public LienDocMetaGroupe iconeContentType(String iconeContentType) {
        this.iconeContentType = iconeContentType;
        return this;
    }

    public void setIconeContentType(String iconeContentType) {
        this.iconeContentType = iconeContentType;
    }

    public byte[] getImage() {
        return image;
    }

    public LienDocMetaGroupe image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public LienDocMetaGroupe imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getDoc() {
        return doc;
    }

    public LienDocMetaGroupe doc(byte[] doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public LienDocMetaGroupe docContentType(String docContentType) {
        this.docContentType = docContentType;
        return this;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public String getDetail() {
        return detail;
    }

    public LienDocMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MetaGroupe getGroupe() {
        return groupe;
    }

    public LienDocMetaGroupe groupe(MetaGroupe metaGroupe) {
        this.groupe = metaGroupe;
        return this;
    }

    public void setGroupe(MetaGroupe metaGroupe) {
        this.groupe = metaGroupe;
    }

    public TypeMetaGroupe getType() {
        return type;
    }

    public LienDocMetaGroupe type(TypeMetaGroupe typeMetaGroupe) {
        this.type = typeMetaGroupe;
        return this;
    }

    public void setType(TypeMetaGroupe typeMetaGroupe) {
        this.type = typeMetaGroupe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LienDocMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((LienDocMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LienDocMetaGroupe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", typeDuDoc='" + getTypeDuDoc() + "'" +
            ", icone='" + getIcone() + "'" +
            ", iconeContentType='" + getIconeContentType() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", doc='" + getDoc() + "'" +
            ", docContentType='" + getDocContentType() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
