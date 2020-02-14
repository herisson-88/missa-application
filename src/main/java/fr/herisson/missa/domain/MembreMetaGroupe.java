package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MembreMetaGroupe.
 */
@Entity
@Table(name = "membre_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "membremetagroupe")
public class MembreMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "validated")
    private Boolean validated;

    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @Column(name = "admin")
    private Boolean admin;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "presentation", nullable = false)
    private String presentation;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connaissance", nullable = false)
    private String connaissance;

    @NotNull
    @Column(name = "mailing_list", nullable = false)
    private Boolean mailingList;

    @ManyToOne
    @JsonIgnoreProperties("membres")
    private MetaGroupe groupeMembre;

    @ManyToOne
    @JsonIgnoreProperties("membreValides")
    private MissaUser validePar;

    @ManyToOne
    @JsonIgnoreProperties("membres")
    private MissaUser missaUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isValidated() {
        return validated;
    }

    public MembreMetaGroupe validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public MembreMetaGroupe dateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public MembreMetaGroupe admin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPresentation() {
        return presentation;
    }

    public MembreMetaGroupe presentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getConnaissance() {
        return connaissance;
    }

    public MembreMetaGroupe connaissance(String connaissance) {
        this.connaissance = connaissance;
        return this;
    }

    public void setConnaissance(String connaissance) {
        this.connaissance = connaissance;
    }

    public Boolean isMailingList() {
        return mailingList;
    }

    public MembreMetaGroupe mailingList(Boolean mailingList) {
        this.mailingList = mailingList;
        return this;
    }

    public void setMailingList(Boolean mailingList) {
        this.mailingList = mailingList;
    }

    public MetaGroupe getGroupeMembre() {
        return groupeMembre;
    }

    public MembreMetaGroupe groupeMembre(MetaGroupe metaGroupe) {
        this.groupeMembre = metaGroupe;
        return this;
    }

    public void setGroupeMembre(MetaGroupe metaGroupe) {
        this.groupeMembre = metaGroupe;
    }

    public MissaUser getValidePar() {
        return validePar;
    }

    public MembreMetaGroupe validePar(MissaUser missaUser) {
        this.validePar = missaUser;
        return this;
    }

    public void setValidePar(MissaUser missaUser) {
        this.validePar = missaUser;
    }

    public MissaUser getMissaUser() {
        return missaUser;
    }

    public MembreMetaGroupe missaUser(MissaUser missaUser) {
        this.missaUser = missaUser;
        return this;
    }

    public void setMissaUser(MissaUser missaUser) {
        this.missaUser = missaUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembreMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((MembreMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MembreMetaGroupe{" +
            "id=" + getId() +
            ", validated='" + isValidated() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", admin='" + isAdmin() + "'" +
            ", presentation='" + getPresentation() + "'" +
            ", connaissance='" + getConnaissance() + "'" +
            ", mailingList='" + isMailingList() + "'" +
            "}";
    }
}
