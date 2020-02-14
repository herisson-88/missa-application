package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Connaissance.
 */
@Entity
@Table(name = "connaissance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "connaissance")
public class Connaissance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "nom", length = 100)
    private String nom;

    @Size(max = 100)
    @Column(name = "prenom", length = 100)
    private String prenom;

    @Size(max = 100)
    @Column(name = "mail", length = 100)
    private String mail;

    @Size(max = 200)
    @Column(name = "id_face_book", length = 200)
    private String idFaceBook;

    @Column(name = "parraine")
    private Boolean parraine;

    @ManyToOne
    @JsonIgnoreProperties("connais")
    private MissaUser connuPar;

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

    public Connaissance nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Connaissance prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public Connaissance mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdFaceBook() {
        return idFaceBook;
    }

    public Connaissance idFaceBook(String idFaceBook) {
        this.idFaceBook = idFaceBook;
        return this;
    }

    public void setIdFaceBook(String idFaceBook) {
        this.idFaceBook = idFaceBook;
    }

    public Boolean isParraine() {
        return parraine;
    }

    public Connaissance parraine(Boolean parraine) {
        this.parraine = parraine;
        return this;
    }

    public void setParraine(Boolean parraine) {
        this.parraine = parraine;
    }

    public MissaUser getConnuPar() {
        return connuPar;
    }

    public Connaissance connuPar(MissaUser missaUser) {
        this.connuPar = missaUser;
        return this;
    }

    public void setConnuPar(MissaUser missaUser) {
        this.connuPar = missaUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connaissance)) {
            return false;
        }
        return id != null && id.equals(((Connaissance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Connaissance{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            ", idFaceBook='" + getIdFaceBook() + "'" +
            ", parraine='" + isParraine() + "'" +
            "}";
    }
}
