package fr.herisson.missa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.herisson.missa.domain.enumeration.EtatUser;

/**
 * A MissaUser.
 */
@Entity
@Table(name = "missa_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "missauser")
public class MissaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code_postal", length = 20, nullable = false)
    private String codePostal;

    @NotNull
    @Size(max = 20)
    @Column(name = "initiales", length = 20, nullable = false)
    private String initiales;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @NotNull
    @Size(max = 50)
    @Column(name = "prenom", length = 50, nullable = false)
    private String prenom;

    @NotNull
    @Size(max = 50)
    @Column(name = "mail", length = 50, nullable = false)
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatUser etat;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "connuPar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Connaissance> connais = new HashSet<>();

    @OneToMany(mappedBy = "validePar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MembreMetaGroupe> membreValides = new HashSet<>();

    @OneToMany(mappedBy = "missaUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MembreMetaGroupe> membres = new HashSet<>();

    @OneToMany(mappedBy = "auteur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MessageMetaGroupe> messagesDeMois = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EnvoiDeMail> mails = new HashSet<>();

    @OneToMany(mappedBy = "auteurPartage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PartageMetaGroupe> demandesPartages = new HashSet<>();

    @OneToMany(mappedBy = "validateurDuPartage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PartageMetaGroupe> aValidePartages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public MissaUser codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getInitiales() {
        return initiales;
    }

    public MissaUser initiales(String initiales) {
        this.initiales = initiales;
        return this;
    }

    public void setInitiales(String initiales) {
        this.initiales = initiales;
    }

    public String getNom() {
        return nom;
    }

    public MissaUser nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public MissaUser prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public MissaUser mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EtatUser getEtat() {
        return etat;
    }

    public MissaUser etat(EtatUser etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatUser etat) {
        this.etat = etat;
    }

    public User getUser() {
        return user;
    }

    public MissaUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Connaissance> getConnais() {
        return connais;
    }

    public MissaUser connais(Set<Connaissance> connaissances) {
        this.connais = connaissances;
        return this;
    }

    public MissaUser addConnais(Connaissance connaissance) {
        this.connais.add(connaissance);
        connaissance.setConnuPar(this);
        return this;
    }

    public MissaUser removeConnais(Connaissance connaissance) {
        this.connais.remove(connaissance);
        connaissance.setConnuPar(null);
        return this;
    }

    public void setConnais(Set<Connaissance> connaissances) {
        this.connais = connaissances;
    }

    public Set<MembreMetaGroupe> getMembreValides() {
        return membreValides;
    }

    public MissaUser membreValides(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membreValides = membreMetaGroupes;
        return this;
    }

    public MissaUser addMembreValides(MembreMetaGroupe membreMetaGroupe) {
        this.membreValides.add(membreMetaGroupe);
        membreMetaGroupe.setValidePar(this);
        return this;
    }

    public MissaUser removeMembreValides(MembreMetaGroupe membreMetaGroupe) {
        this.membreValides.remove(membreMetaGroupe);
        membreMetaGroupe.setValidePar(null);
        return this;
    }

    public void setMembreValides(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membreValides = membreMetaGroupes;
    }

    public Set<MembreMetaGroupe> getMembres() {
        return membres;
    }

    public MissaUser membres(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membres = membreMetaGroupes;
        return this;
    }

    public MissaUser addMembres(MembreMetaGroupe membreMetaGroupe) {
        this.membres.add(membreMetaGroupe);
        membreMetaGroupe.setMissaUser(this);
        return this;
    }

    public MissaUser removeMembres(MembreMetaGroupe membreMetaGroupe) {
        this.membres.remove(membreMetaGroupe);
        membreMetaGroupe.setMissaUser(null);
        return this;
    }

    public void setMembres(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membres = membreMetaGroupes;
    }

    public Set<MessageMetaGroupe> getMessagesDeMois() {
        return messagesDeMois;
    }

    public MissaUser messagesDeMois(Set<MessageMetaGroupe> messageMetaGroupes) {
        this.messagesDeMois = messageMetaGroupes;
        return this;
    }

    public MissaUser addMessagesDeMoi(MessageMetaGroupe messageMetaGroupe) {
        this.messagesDeMois.add(messageMetaGroupe);
        messageMetaGroupe.setAuteur(this);
        return this;
    }

    public MissaUser removeMessagesDeMoi(MessageMetaGroupe messageMetaGroupe) {
        this.messagesDeMois.remove(messageMetaGroupe);
        messageMetaGroupe.setAuteur(null);
        return this;
    }

    public void setMessagesDeMois(Set<MessageMetaGroupe> messageMetaGroupes) {
        this.messagesDeMois = messageMetaGroupes;
    }

    public Set<EnvoiDeMail> getMails() {
        return mails;
    }

    public MissaUser mails(Set<EnvoiDeMail> envoiDeMails) {
        this.mails = envoiDeMails;
        return this;
    }

    public MissaUser addMails(EnvoiDeMail envoiDeMail) {
        this.mails.add(envoiDeMail);
        envoiDeMail.setAdmin(this);
        return this;
    }

    public MissaUser removeMails(EnvoiDeMail envoiDeMail) {
        this.mails.remove(envoiDeMail);
        envoiDeMail.setAdmin(null);
        return this;
    }

    public void setMails(Set<EnvoiDeMail> envoiDeMails) {
        this.mails = envoiDeMails;
    }

    public Set<PartageMetaGroupe> getDemandesPartages() {
        return demandesPartages;
    }

    public MissaUser demandesPartages(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.demandesPartages = partageMetaGroupes;
        return this;
    }

    public MissaUser addDemandesPartages(PartageMetaGroupe partageMetaGroupe) {
        this.demandesPartages.add(partageMetaGroupe);
        partageMetaGroupe.setAuteurPartage(this);
        return this;
    }

    public MissaUser removeDemandesPartages(PartageMetaGroupe partageMetaGroupe) {
        this.demandesPartages.remove(partageMetaGroupe);
        partageMetaGroupe.setAuteurPartage(null);
        return this;
    }

    public void setDemandesPartages(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.demandesPartages = partageMetaGroupes;
    }

    public Set<PartageMetaGroupe> getAValidePartages() {
        return aValidePartages;
    }

    public MissaUser aValidePartages(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.aValidePartages = partageMetaGroupes;
        return this;
    }

    public MissaUser addAValidePartages(PartageMetaGroupe partageMetaGroupe) {
        this.aValidePartages.add(partageMetaGroupe);
        partageMetaGroupe.setValidateurDuPartage(this);
        return this;
    }

    public MissaUser removeAValidePartages(PartageMetaGroupe partageMetaGroupe) {
        this.aValidePartages.remove(partageMetaGroupe);
        partageMetaGroupe.setValidateurDuPartage(null);
        return this;
    }

    public void setAValidePartages(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.aValidePartages = partageMetaGroupes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MissaUser)) {
            return false;
        }
        return id != null && id.equals(((MissaUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MissaUser{" +
            "id=" + getId() +
            ", codePostal='" + getCodePostal() + "'" +
            ", initiales='" + getInitiales() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
