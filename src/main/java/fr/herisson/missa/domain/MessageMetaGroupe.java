package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MessageMetaGroupe.
 */
@Entity
@Table(name = "message_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "messagemetagroupe")
public class MessageMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "titre", length = 100)
    private String titre;

    @Column(name = "publique")
    private Boolean publique;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JsonIgnoreProperties("messagesDeMois")
    private MissaUser auteur;

    @ManyToOne
    @JsonIgnoreProperties("messagesDuGroupes")
    private MetaGroupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public MessageMetaGroupe titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Boolean isPublique() {
        return publique;
    }

    public MessageMetaGroupe publique(Boolean publique) {
        this.publique = publique;
        return this;
    }

    public void setPublique(Boolean publique) {
        this.publique = publique;
    }

    public String getMessage() {
        return message;
    }

    public MessageMetaGroupe message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MissaUser getAuteur() {
        return auteur;
    }

    public MessageMetaGroupe auteur(MissaUser missaUser) {
        this.auteur = missaUser;
        return this;
    }

    public void setAuteur(MissaUser missaUser) {
        this.auteur = missaUser;
    }

    public MetaGroupe getGroupe() {
        return groupe;
    }

    public MessageMetaGroupe groupe(MetaGroupe metaGroupe) {
        this.groupe = metaGroupe;
        return this;
    }

    public void setGroupe(MetaGroupe metaGroupe) {
        this.groupe = metaGroupe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((MessageMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MessageMetaGroupe{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", publique='" + isPublique() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
