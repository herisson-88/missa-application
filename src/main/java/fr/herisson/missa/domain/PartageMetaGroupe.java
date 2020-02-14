package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PartageMetaGroupe.
 */
@Entity
@Table(name = "partage_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "partagemetagroupe")
public class PartageMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "validated")
    private Boolean validated;

    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JsonIgnoreProperties("partagesVers")
    private MetaGroupe metaGroupePartage;

    @ManyToOne
    @JsonIgnoreProperties("partagesRecuses")
    private MetaGroupe metaGroupeDestinataires;

    @ManyToOne
    @JsonIgnoreProperties("demandesPartages")
    private MissaUser auteurPartage;

    @ManyToOne
    @JsonIgnoreProperties("aValidePartages")
    private MissaUser validateurDuPartage;

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

    public PartageMetaGroupe validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public PartageMetaGroupe dateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getDetail() {
        return detail;
    }

    public PartageMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MetaGroupe getMetaGroupePartage() {
        return metaGroupePartage;
    }

    public PartageMetaGroupe metaGroupePartage(MetaGroupe metaGroupe) {
        this.metaGroupePartage = metaGroupe;
        return this;
    }

    public void setMetaGroupePartage(MetaGroupe metaGroupe) {
        this.metaGroupePartage = metaGroupe;
    }

    public MetaGroupe getMetaGroupeDestinataires() {
        return metaGroupeDestinataires;
    }

    public PartageMetaGroupe metaGroupeDestinataires(MetaGroupe metaGroupe) {
        this.metaGroupeDestinataires = metaGroupe;
        return this;
    }

    public void setMetaGroupeDestinataires(MetaGroupe metaGroupe) {
        this.metaGroupeDestinataires = metaGroupe;
    }

    public MissaUser getAuteurPartage() {
        return auteurPartage;
    }

    public PartageMetaGroupe auteurPartage(MissaUser missaUser) {
        this.auteurPartage = missaUser;
        return this;
    }

    public void setAuteurPartage(MissaUser missaUser) {
        this.auteurPartage = missaUser;
    }

    public MissaUser getValidateurDuPartage() {
        return validateurDuPartage;
    }

    public PartageMetaGroupe validateurDuPartage(MissaUser missaUser) {
        this.validateurDuPartage = missaUser;
        return this;
    }

    public void setValidateurDuPartage(MissaUser missaUser) {
        this.validateurDuPartage = missaUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartageMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((PartageMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PartageMetaGroupe{" +
            "id=" + getId() +
            ", validated='" + isValidated() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
