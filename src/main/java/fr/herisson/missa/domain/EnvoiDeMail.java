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
import java.util.HashSet;
import java.util.Set;

/**
 * A EnvoiDeMail.
 */
@Entity
@Table(name = "envoi_de_mail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "envoidemail")
public class EnvoiDeMail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_envoi", nullable = false)
    private ZonedDateTime dateEnvoi;

    @NotNull
    @Size(max = 200)
    @Column(name = "titre", length = 200, nullable = false)
    private String titre;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "adresse_mail", nullable = false)
    private String adresseMail;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "mot_spirituel")
    private String motSpirituel;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "conseil_technique")
    private String conseilTechnique;

    @NotNull
    @Column(name = "nb_destinataire", nullable = false)
    private Integer nbDestinataire;

    @Column(name = "sended")
    private Boolean sended;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "envoi_de_mail_groupe_partage_par_mail",
               joinColumns = @JoinColumn(name = "envoi_de_mail_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "groupe_partage_par_mail_id", referencedColumnName = "id"))
    private Set<MetaGroupe> groupePartageParMails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("mails")
    private MissaUser admin;

    @ManyToOne
    @JsonIgnoreProperties("mails")
    private MetaGroupe groupeDuMail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public EnvoiDeMail dateEnvoi(ZonedDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
        return this;
    }

    public void setDateEnvoi(ZonedDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getTitre() {
        return titre;
    }

    public EnvoiDeMail titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public EnvoiDeMail adresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
        return this;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMotSpirituel() {
        return motSpirituel;
    }

    public EnvoiDeMail motSpirituel(String motSpirituel) {
        this.motSpirituel = motSpirituel;
        return this;
    }

    public void setMotSpirituel(String motSpirituel) {
        this.motSpirituel = motSpirituel;
    }

    public String getConseilTechnique() {
        return conseilTechnique;
    }

    public EnvoiDeMail conseilTechnique(String conseilTechnique) {
        this.conseilTechnique = conseilTechnique;
        return this;
    }

    public void setConseilTechnique(String conseilTechnique) {
        this.conseilTechnique = conseilTechnique;
    }

    public Integer getNbDestinataire() {
        return nbDestinataire;
    }

    public EnvoiDeMail nbDestinataire(Integer nbDestinataire) {
        this.nbDestinataire = nbDestinataire;
        return this;
    }

    public void setNbDestinataire(Integer nbDestinataire) {
        this.nbDestinataire = nbDestinataire;
    }

    public Boolean isSended() {
        return sended;
    }

    public EnvoiDeMail sended(Boolean sended) {
        this.sended = sended;
        return this;
    }

    public void setSended(Boolean sended) {
        this.sended = sended;
    }

    public Set<MetaGroupe> getGroupePartageParMails() {
        return groupePartageParMails;
    }

    public EnvoiDeMail groupePartageParMails(Set<MetaGroupe> metaGroupes) {
        this.groupePartageParMails = metaGroupes;
        return this;
    }

    public EnvoiDeMail addGroupePartageParMail(MetaGroupe metaGroupe) {
        this.groupePartageParMails.add(metaGroupe);
        metaGroupe.getMessageMailReferents().add(this);
        return this;
    }

    public EnvoiDeMail removeGroupePartageParMail(MetaGroupe metaGroupe) {
        this.groupePartageParMails.remove(metaGroupe);
        metaGroupe.getMessageMailReferents().remove(this);
        return this;
    }

    public void setGroupePartageParMails(Set<MetaGroupe> metaGroupes) {
        this.groupePartageParMails = metaGroupes;
    }

    public MissaUser getAdmin() {
        return admin;
    }

    public EnvoiDeMail admin(MissaUser missaUser) {
        this.admin = missaUser;
        return this;
    }

    public void setAdmin(MissaUser missaUser) {
        this.admin = missaUser;
    }

    public MetaGroupe getGroupeDuMail() {
        return groupeDuMail;
    }

    public EnvoiDeMail groupeDuMail(MetaGroupe metaGroupe) {
        this.groupeDuMail = metaGroupe;
        return this;
    }

    public void setGroupeDuMail(MetaGroupe metaGroupe) {
        this.groupeDuMail = metaGroupe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvoiDeMail)) {
            return false;
        }
        return id != null && id.equals(((EnvoiDeMail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EnvoiDeMail{" +
            "id=" + getId() +
            ", dateEnvoi='" + getDateEnvoi() + "'" +
            ", titre='" + getTitre() + "'" +
            ", adresseMail='" + getAdresseMail() + "'" +
            ", motSpirituel='" + getMotSpirituel() + "'" +
            ", conseilTechnique='" + getConseilTechnique() + "'" +
            ", nbDestinataire=" + getNbDestinataire() +
            ", sended='" + isSended() + "'" +
            "}";
    }
}
