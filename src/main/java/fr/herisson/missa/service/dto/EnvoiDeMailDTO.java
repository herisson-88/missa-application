package fr.herisson.missa.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link fr.herisson.missa.domain.EnvoiDeMail} entity.
 */
public class EnvoiDeMailDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateEnvoi;

    @NotNull
    @Size(max = 200)
    private String titre;

    
    @Lob
    private String adresseMail;

    @Lob
    private String motSpirituel;

    @Lob
    private String conseilTechnique;

    @NotNull
    private Integer nbDestinataire;

    private Boolean sended;


    private Set<MetaGroupeDTO> groupePartageParMails = new HashSet<>();

    private Long adminId;

    private Long groupeDuMailId;

    private String groupeDuMailNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(ZonedDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMotSpirituel() {
        return motSpirituel;
    }

    public void setMotSpirituel(String motSpirituel) {
        this.motSpirituel = motSpirituel;
    }

    public String getConseilTechnique() {
        return conseilTechnique;
    }

    public void setConseilTechnique(String conseilTechnique) {
        this.conseilTechnique = conseilTechnique;
    }

    public Integer getNbDestinataire() {
        return nbDestinataire;
    }

    public void setNbDestinataire(Integer nbDestinataire) {
        this.nbDestinataire = nbDestinataire;
    }

    public Boolean isSended() {
        return sended;
    }

    public void setSended(Boolean sended) {
        this.sended = sended;
    }

    public Set<MetaGroupeDTO> getGroupePartageParMails() {
        return groupePartageParMails;
    }

    public void setGroupePartageParMails(Set<MetaGroupeDTO> metaGroupes) {
        this.groupePartageParMails = metaGroupes;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long missaUserId) {
        this.adminId = missaUserId;
    }

    public Long getGroupeDuMailId() {
        return groupeDuMailId;
    }

    public void setGroupeDuMailId(Long metaGroupeId) {
        this.groupeDuMailId = metaGroupeId;
    }

    public String getGroupeDuMailNom() {
        return groupeDuMailNom;
    }

    public void setGroupeDuMailNom(String metaGroupeNom) {
        this.groupeDuMailNom = metaGroupeNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnvoiDeMailDTO envoiDeMailDTO = (EnvoiDeMailDTO) o;
        if (envoiDeMailDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), envoiDeMailDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnvoiDeMailDTO{" +
            "id=" + getId() +
            ", dateEnvoi='" + getDateEnvoi() + "'" +
            ", titre='" + getTitre() + "'" +
            ", adresseMail='" + getAdresseMail() + "'" +
            ", motSpirituel='" + getMotSpirituel() + "'" +
            ", conseilTechnique='" + getConseilTechnique() + "'" +
            ", nbDestinataire=" + getNbDestinataire() +
            ", sended='" + isSended() + "'" +
            ", adminId=" + getAdminId() +
            ", groupeDuMailId=" + getGroupeDuMailId() +
            ", groupeDuMailNom='" + getGroupeDuMailNom() + "'" +
            "}";
    }
}
