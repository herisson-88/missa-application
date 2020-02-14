package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import fr.herisson.missa.domain.enumeration.MembreDiffusion;

import fr.herisson.missa.domain.enumeration.Visibilite;

/**
 * A MetaGroupe.
 */
@Entity
@Table(name = "meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "metagroupe")
public class MetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "autorite_validation")
    private Boolean autoriteValidation;

    @Enumerated(EnumType.STRING)
    @Column(name = "membre_parent")
    private MembreDiffusion membreParent;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibilite")
    private Visibilite visibilite;

    @Column(name = "droit_envoi_de_mail")
    private Boolean droitEnvoiDeMail;

    @Column(name = "demande_diffusion_verticale")
    private Boolean demandeDiffusionVerticale;

    @Column(name = "messagerie_moderee")
    private Boolean messagerieModeree;

    @Column(name = "groupe_final")
    private Boolean groupeFinal;

    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @OneToMany(mappedBy = "groupeMembre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MembreMetaGroupe> membres = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DateMetaGroupe> dates = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LieuMetaGroupe> lieus = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LienDocMetaGroupe> docs = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrganisateurMetaGroupe> coordonneesOrganisateurs = new HashSet<>();

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MetaGroupe> sousGroupes = new HashSet<>();

    @OneToMany(mappedBy = "groupe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MessageMetaGroupe> messagesDuGroupes = new HashSet<>();

    @OneToMany(mappedBy = "groupeDuMail")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EnvoiDeMail> mails = new HashSet<>();

    @OneToMany(mappedBy = "metaGroupePartage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PartageMetaGroupe> partagesVers = new HashSet<>();

    @OneToMany(mappedBy = "metaGroupeDestinataires")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PartageMetaGroupe> partagesRecuses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sousGroupes")
    private MetaGroupe parent;

    @ManyToOne
    @JsonIgnoreProperties("groupes")
    private TypeMetaGroupe type;

    @ManyToOne
    @JsonIgnoreProperties("groupes")
    private Reseau reseau;

    @ManyToMany(mappedBy = "groupePartageParMails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<EnvoiDeMail> messageMailReferents = new HashSet<>();

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

    public MetaGroupe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isAutoriteValidation() {
        return autoriteValidation;
    }

    public MetaGroupe autoriteValidation(Boolean autoriteValidation) {
        this.autoriteValidation = autoriteValidation;
        return this;
    }

    public void setAutoriteValidation(Boolean autoriteValidation) {
        this.autoriteValidation = autoriteValidation;
    }

    public MembreDiffusion getMembreParent() {
        return membreParent;
    }

    public MetaGroupe membreParent(MembreDiffusion membreParent) {
        this.membreParent = membreParent;
        return this;
    }

    public void setMembreParent(MembreDiffusion membreParent) {
        this.membreParent = membreParent;
    }

    public Visibilite getVisibilite() {
        return visibilite;
    }

    public MetaGroupe visibilite(Visibilite visibilite) {
        this.visibilite = visibilite;
        return this;
    }

    public void setVisibilite(Visibilite visibilite) {
        this.visibilite = visibilite;
    }

    public Boolean isDroitEnvoiDeMail() {
        return droitEnvoiDeMail;
    }

    public MetaGroupe droitEnvoiDeMail(Boolean droitEnvoiDeMail) {
        this.droitEnvoiDeMail = droitEnvoiDeMail;
        return this;
    }

    public void setDroitEnvoiDeMail(Boolean droitEnvoiDeMail) {
        this.droitEnvoiDeMail = droitEnvoiDeMail;
    }

    public Boolean isDemandeDiffusionVerticale() {
        return demandeDiffusionVerticale;
    }

    public MetaGroupe demandeDiffusionVerticale(Boolean demandeDiffusionVerticale) {
        this.demandeDiffusionVerticale = demandeDiffusionVerticale;
        return this;
    }

    public void setDemandeDiffusionVerticale(Boolean demandeDiffusionVerticale) {
        this.demandeDiffusionVerticale = demandeDiffusionVerticale;
    }

    public Boolean isMessagerieModeree() {
        return messagerieModeree;
    }

    public MetaGroupe messagerieModeree(Boolean messagerieModeree) {
        this.messagerieModeree = messagerieModeree;
        return this;
    }

    public void setMessagerieModeree(Boolean messagerieModeree) {
        this.messagerieModeree = messagerieModeree;
    }

    public Boolean isGroupeFinal() {
        return groupeFinal;
    }

    public MetaGroupe groupeFinal(Boolean groupeFinal) {
        this.groupeFinal = groupeFinal;
        return this;
    }

    public void setGroupeFinal(Boolean groupeFinal) {
        this.groupeFinal = groupeFinal;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public MetaGroupe dateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getDetail() {
        return detail;
    }

    public MetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<MembreMetaGroupe> getMembres() {
        return membres;
    }

    public MetaGroupe membres(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membres = membreMetaGroupes;
        return this;
    }

    public MetaGroupe addMembres(MembreMetaGroupe membreMetaGroupe) {
        this.membres.add(membreMetaGroupe);
        membreMetaGroupe.setGroupeMembre(this);
        return this;
    }

    public MetaGroupe removeMembres(MembreMetaGroupe membreMetaGroupe) {
        this.membres.remove(membreMetaGroupe);
        membreMetaGroupe.setGroupeMembre(null);
        return this;
    }

    public void setMembres(Set<MembreMetaGroupe> membreMetaGroupes) {
        this.membres = membreMetaGroupes;
    }

    public Set<DateMetaGroupe> getDates() {
        return dates;
    }

    public MetaGroupe dates(Set<DateMetaGroupe> dateMetaGroupes) {
        this.dates = dateMetaGroupes;
        return this;
    }

    public MetaGroupe addDates(DateMetaGroupe dateMetaGroupe) {
        this.dates.add(dateMetaGroupe);
        dateMetaGroupe.setGroupe(this);
        return this;
    }

    public MetaGroupe removeDates(DateMetaGroupe dateMetaGroupe) {
        this.dates.remove(dateMetaGroupe);
        dateMetaGroupe.setGroupe(null);
        return this;
    }

    public void setDates(Set<DateMetaGroupe> dateMetaGroupes) {
        this.dates = dateMetaGroupes;
    }

    public Set<LieuMetaGroupe> getLieus() {
        return lieus;
    }

    public MetaGroupe lieus(Set<LieuMetaGroupe> lieuMetaGroupes) {
        this.lieus = lieuMetaGroupes;
        return this;
    }

    public MetaGroupe addLieu(LieuMetaGroupe lieuMetaGroupe) {
        this.lieus.add(lieuMetaGroupe);
        lieuMetaGroupe.setGroupe(this);
        return this;
    }

    public MetaGroupe removeLieu(LieuMetaGroupe lieuMetaGroupe) {
        this.lieus.remove(lieuMetaGroupe);
        lieuMetaGroupe.setGroupe(null);
        return this;
    }

    public void setLieus(Set<LieuMetaGroupe> lieuMetaGroupes) {
        this.lieus = lieuMetaGroupes;
    }

    public Set<LienDocMetaGroupe> getDocs() {
        return docs;
    }

    public MetaGroupe docs(Set<LienDocMetaGroupe> lienDocMetaGroupes) {
        this.docs = lienDocMetaGroupes;
        return this;
    }

    public MetaGroupe addDocs(LienDocMetaGroupe lienDocMetaGroupe) {
        this.docs.add(lienDocMetaGroupe);
        lienDocMetaGroupe.setGroupe(this);
        return this;
    }

    public MetaGroupe removeDocs(LienDocMetaGroupe lienDocMetaGroupe) {
        this.docs.remove(lienDocMetaGroupe);
        lienDocMetaGroupe.setGroupe(null);
        return this;
    }

    public void setDocs(Set<LienDocMetaGroupe> lienDocMetaGroupes) {
        this.docs = lienDocMetaGroupes;
    }

    public Set<OrganisateurMetaGroupe> getCoordonneesOrganisateurs() {
        return coordonneesOrganisateurs;
    }

    public MetaGroupe coordonneesOrganisateurs(Set<OrganisateurMetaGroupe> organisateurMetaGroupes) {
        this.coordonneesOrganisateurs = organisateurMetaGroupes;
        return this;
    }

    public MetaGroupe addCoordonneesOrganisateurs(OrganisateurMetaGroupe organisateurMetaGroupe) {
        this.coordonneesOrganisateurs.add(organisateurMetaGroupe);
        organisateurMetaGroupe.setGroupe(this);
        return this;
    }

    public MetaGroupe removeCoordonneesOrganisateurs(OrganisateurMetaGroupe organisateurMetaGroupe) {
        this.coordonneesOrganisateurs.remove(organisateurMetaGroupe);
        organisateurMetaGroupe.setGroupe(null);
        return this;
    }

    public void setCoordonneesOrganisateurs(Set<OrganisateurMetaGroupe> organisateurMetaGroupes) {
        this.coordonneesOrganisateurs = organisateurMetaGroupes;
    }

    public Set<MetaGroupe> getSousGroupes() {
        return sousGroupes;
    }

    public MetaGroupe sousGroupes(Set<MetaGroupe> metaGroupes) {
        this.sousGroupes = metaGroupes;
        return this;
    }

    public MetaGroupe addSousGroupes(MetaGroupe metaGroupe) {
        this.sousGroupes.add(metaGroupe);
        metaGroupe.setParent(this);
        return this;
    }

    public MetaGroupe removeSousGroupes(MetaGroupe metaGroupe) {
        this.sousGroupes.remove(metaGroupe);
        metaGroupe.setParent(null);
        return this;
    }

    public void setSousGroupes(Set<MetaGroupe> metaGroupes) {
        this.sousGroupes = metaGroupes;
    }

    public Set<MessageMetaGroupe> getMessagesDuGroupes() {
        return messagesDuGroupes;
    }

    public MetaGroupe messagesDuGroupes(Set<MessageMetaGroupe> messageMetaGroupes) {
        this.messagesDuGroupes = messageMetaGroupes;
        return this;
    }

    public MetaGroupe addMessagesDuGroupe(MessageMetaGroupe messageMetaGroupe) {
        this.messagesDuGroupes.add(messageMetaGroupe);
        messageMetaGroupe.setGroupe(this);
        return this;
    }

    public MetaGroupe removeMessagesDuGroupe(MessageMetaGroupe messageMetaGroupe) {
        this.messagesDuGroupes.remove(messageMetaGroupe);
        messageMetaGroupe.setGroupe(null);
        return this;
    }

    public void setMessagesDuGroupes(Set<MessageMetaGroupe> messageMetaGroupes) {
        this.messagesDuGroupes = messageMetaGroupes;
    }

    public Set<EnvoiDeMail> getMails() {
        return mails;
    }

    public MetaGroupe mails(Set<EnvoiDeMail> envoiDeMails) {
        this.mails = envoiDeMails;
        return this;
    }

    public MetaGroupe addMails(EnvoiDeMail envoiDeMail) {
        this.mails.add(envoiDeMail);
        envoiDeMail.setGroupeDuMail(this);
        return this;
    }

    public MetaGroupe removeMails(EnvoiDeMail envoiDeMail) {
        this.mails.remove(envoiDeMail);
        envoiDeMail.setGroupeDuMail(null);
        return this;
    }

    public void setMails(Set<EnvoiDeMail> envoiDeMails) {
        this.mails = envoiDeMails;
    }

    public Set<PartageMetaGroupe> getPartagesVers() {
        return partagesVers;
    }

    public MetaGroupe partagesVers(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.partagesVers = partageMetaGroupes;
        return this;
    }

    public MetaGroupe addPartagesVers(PartageMetaGroupe partageMetaGroupe) {
        this.partagesVers.add(partageMetaGroupe);
        partageMetaGroupe.setMetaGroupePartage(this);
        return this;
    }

    public MetaGroupe removePartagesVers(PartageMetaGroupe partageMetaGroupe) {
        this.partagesVers.remove(partageMetaGroupe);
        partageMetaGroupe.setMetaGroupePartage(null);
        return this;
    }

    public void setPartagesVers(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.partagesVers = partageMetaGroupes;
    }

    public Set<PartageMetaGroupe> getPartagesRecuses() {
        return partagesRecuses;
    }

    public MetaGroupe partagesRecuses(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.partagesRecuses = partageMetaGroupes;
        return this;
    }

    public MetaGroupe addPartagesRecus(PartageMetaGroupe partageMetaGroupe) {
        this.partagesRecuses.add(partageMetaGroupe);
        partageMetaGroupe.setMetaGroupeDestinataires(this);
        return this;
    }

    public MetaGroupe removePartagesRecus(PartageMetaGroupe partageMetaGroupe) {
        this.partagesRecuses.remove(partageMetaGroupe);
        partageMetaGroupe.setMetaGroupeDestinataires(null);
        return this;
    }

    public void setPartagesRecuses(Set<PartageMetaGroupe> partageMetaGroupes) {
        this.partagesRecuses = partageMetaGroupes;
    }

    public MetaGroupe getParent() {
        return parent;
    }

    public MetaGroupe parent(MetaGroupe metaGroupe) {
        this.parent = metaGroupe;
        return this;
    }

    public void setParent(MetaGroupe metaGroupe) {
        this.parent = metaGroupe;
    }

    public TypeMetaGroupe getType() {
        return type;
    }

    public MetaGroupe type(TypeMetaGroupe typeMetaGroupe) {
        this.type = typeMetaGroupe;
        return this;
    }

    public void setType(TypeMetaGroupe typeMetaGroupe) {
        this.type = typeMetaGroupe;
    }

    public Reseau getReseau() {
        return reseau;
    }

    public MetaGroupe reseau(Reseau reseau) {
        this.reseau = reseau;
        return this;
    }

    public void setReseau(Reseau reseau) {
        this.reseau = reseau;
    }

    public Set<EnvoiDeMail> getMessageMailReferents() {
        return messageMailReferents;
    }

    public MetaGroupe messageMailReferents(Set<EnvoiDeMail> envoiDeMails) {
        this.messageMailReferents = envoiDeMails;
        return this;
    }

    public MetaGroupe addMessageMailReferent(EnvoiDeMail envoiDeMail) {
        this.messageMailReferents.add(envoiDeMail);
        envoiDeMail.getGroupePartageParMails().add(this);
        return this;
    }

    public MetaGroupe removeMessageMailReferent(EnvoiDeMail envoiDeMail) {
        this.messageMailReferents.remove(envoiDeMail);
        envoiDeMail.getGroupePartageParMails().remove(this);
        return this;
    }

    public void setMessageMailReferents(Set<EnvoiDeMail> envoiDeMails) {
        this.messageMailReferents = envoiDeMails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((MetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MetaGroupe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", autoriteValidation='" + isAutoriteValidation() + "'" +
            ", membreParent='" + getMembreParent() + "'" +
            ", visibilite='" + getVisibilite() + "'" +
            ", droitEnvoiDeMail='" + isDroitEnvoiDeMail() + "'" +
            ", demandeDiffusionVerticale='" + isDemandeDiffusionVerticale() + "'" +
            ", messagerieModeree='" + isMessagerieModeree() + "'" +
            ", groupeFinal='" + isGroupeFinal() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
