package fr.herisson.missa.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import fr.herisson.missa.domain.enumeration.Day;

/**
 * A DTO for the {@link fr.herisson.missa.domain.DateMetaGroupe} entity.
 */
public class DateMetaGroupeDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateDebut;

    private ZonedDateTime dateFin;

    private Day every;

    private Integer hour;

    private Integer minutes;

    @Lob
    private String detail;


    private Long groupeId;

    private String groupeNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Day getEvery() {
        return every;
    }

    public void setEvery(Day every) {
        this.every = every;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long metaGroupeId) {
        this.groupeId = metaGroupeId;
    }

    public String getGroupeNom() {
        return groupeNom;
    }

    public void setGroupeNom(String metaGroupeNom) {
        this.groupeNom = metaGroupeNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateMetaGroupeDTO dateMetaGroupeDTO = (DateMetaGroupeDTO) o;
        if (dateMetaGroupeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dateMetaGroupeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DateMetaGroupeDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", every='" + getEvery() + "'" +
            ", hour=" + getHour() +
            ", minutes=" + getMinutes() +
            ", detail='" + getDetail() + "'" +
            ", groupeId=" + getGroupeId() +
            ", groupeNom='" + getGroupeNom() + "'" +
            "}";
    }
}
