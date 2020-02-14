package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import fr.herisson.missa.domain.enumeration.Day;

/**
 * A DateMetaGroupe.
 */
@Entity
@Table(name = "date_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "datemetagroupe")
public class DateMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_debut")
    private ZonedDateTime dateDebut;

    @Column(name = "date_fin")
    private ZonedDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "every")
    private Day every;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "minutes")
    private Integer minutes;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JsonIgnoreProperties("dates")
    private MetaGroupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public DateMetaGroupe dateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public DateMetaGroupe dateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Day getEvery() {
        return every;
    }

    public DateMetaGroupe every(Day every) {
        this.every = every;
        return this;
    }

    public void setEvery(Day every) {
        this.every = every;
    }

    public Integer getHour() {
        return hour;
    }

    public DateMetaGroupe hour(Integer hour) {
        this.hour = hour;
        return this;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public DateMetaGroupe minutes(Integer minutes) {
        this.minutes = minutes;
        return this;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getDetail() {
        return detail;
    }

    public DateMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public MetaGroupe getGroupe() {
        return groupe;
    }

    public DateMetaGroupe groupe(MetaGroupe metaGroupe) {
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
        if (!(o instanceof DateMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((DateMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DateMetaGroupe{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", every='" + getEvery() + "'" +
            ", hour=" + getHour() +
            ", minutes=" + getMinutes() +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
