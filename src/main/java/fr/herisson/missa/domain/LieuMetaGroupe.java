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
 * A LieuMetaGroupe.
 */
@Entity
@Table(name = "lieu_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lieumetagroupe")
public class LieuMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Size(max = 100)
    @Column(name = "adresse", length = 100)
    private String adresse;

    @Size(max = 100)
    @Column(name = "code_postal", length = 100)
    private String codePostal;

    @Size(max = 100)
    @Column(name = "ville", length = 100)
    private String ville;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @Size(max = 10)
    @Column(name = "departement_groupe", length = 10)
    private String departementGroupe;

    @ManyToOne
    @JsonIgnoreProperties("lieus")
    private MetaGroupe groupe;

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

    public LieuMetaGroupe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public LieuMetaGroupe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public LieuMetaGroupe codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public LieuMetaGroupe ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Double getLat() {
        return lat;
    }

    public LieuMetaGroupe lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public LieuMetaGroupe lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDetail() {
        return detail;
    }

    public LieuMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDepartementGroupe() {
        return departementGroupe;
    }

    public LieuMetaGroupe departementGroupe(String departementGroupe) {
        this.departementGroupe = departementGroupe;
        return this;
    }

    public void setDepartementGroupe(String departementGroupe) {
        this.departementGroupe = departementGroupe;
    }

    public MetaGroupe getGroupe() {
        return groupe;
    }

    public LieuMetaGroupe groupe(MetaGroupe metaGroupe) {
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
        if (!(o instanceof LieuMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((LieuMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LieuMetaGroupe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", detail='" + getDetail() + "'" +
            ", departementGroupe='" + getDepartementGroupe() + "'" +
            "}";
    }
}
