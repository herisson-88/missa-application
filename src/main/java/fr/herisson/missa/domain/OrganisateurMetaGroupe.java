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
 * A OrganisateurMetaGroupe.
 */
@Entity
@Table(name = "organisateur_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "organisateurmetagroupe")
public class OrganisateurMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 200)
    @Column(name = "site", length = 200)
    private String site;

    @Size(max = 100)
    @Column(name = "nom", length = 100)
    private String nom;

    @Size(max = 100)
    @Column(name = "prenom", length = 100)
    private String prenom;

    @Size(max = 20)
    @Column(name = "telephone", length = 20)
    private String telephone;

    @Size(max = 100)
    @Column(name = "mail", length = 100)
    private String mail;

    @Size(max = 100)
    @Column(name = "adresse", length = 100)
    private String adresse;

    @Size(max = 20)
    @Column(name = "code_postal", length = 20)
    private String codePostal;

    @Size(max = 100)
    @Column(name = "ville", length = 100)
    private String ville;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JsonIgnoreProperties("coordonneesOrganisateurs")
    private MetaGroupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public OrganisateurMetaGroupe site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNom() {
        return nom;
    }

    public OrganisateurMetaGroupe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public OrganisateurMetaGroupe prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public OrganisateurMetaGroupe telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public OrganisateurMetaGroupe mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public OrganisateurMetaGroupe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public OrganisateurMetaGroupe codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public OrganisateurMetaGroupe ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDetail() {
        return detail;
    }

    public OrganisateurMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MetaGroupe getGroupe() {
        return groupe;
    }

    public OrganisateurMetaGroupe groupe(MetaGroupe metaGroupe) {
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
        if (!(o instanceof OrganisateurMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((OrganisateurMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrganisateurMetaGroupe{" +
            "id=" + getId() +
            ", site='" + getSite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mail='" + getMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", ville='" + getVille() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
