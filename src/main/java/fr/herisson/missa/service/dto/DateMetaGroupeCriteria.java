package fr.herisson.missa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.herisson.missa.domain.enumeration.Day;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.DateMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.DateMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /date-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DateMetaGroupeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Day
     */
    public static class DayFilter extends Filter<Day> {

        public DayFilter() {
        }

        public DayFilter(DayFilter filter) {
            super(filter);
        }

        @Override
        public DayFilter copy() {
            return new DayFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dateDebut;

    private ZonedDateTimeFilter dateFin;

    private DayFilter every;

    private IntegerFilter hour;

    private IntegerFilter minutes;

    private LongFilter groupeId;

    public DateMetaGroupeCriteria() {
    }

    public DateMetaGroupeCriteria(DateMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateDebut = other.dateDebut == null ? null : other.dateDebut.copy();
        this.dateFin = other.dateFin == null ? null : other.dateFin.copy();
        this.every = other.every == null ? null : other.every.copy();
        this.hour = other.hour == null ? null : other.hour.copy();
        this.minutes = other.minutes == null ? null : other.minutes.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
    }

    @Override
    public DateMetaGroupeCriteria copy() {
        return new DateMetaGroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTimeFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTimeFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTimeFilter dateFin) {
        this.dateFin = dateFin;
    }

    public DayFilter getEvery() {
        return every;
    }

    public void setEvery(DayFilter every) {
        this.every = every;
    }

    public IntegerFilter getHour() {
        return hour;
    }

    public void setHour(IntegerFilter hour) {
        this.hour = hour;
    }

    public IntegerFilter getMinutes() {
        return minutes;
    }

    public void setMinutes(IntegerFilter minutes) {
        this.minutes = minutes;
    }

    public LongFilter getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(LongFilter groupeId) {
        this.groupeId = groupeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DateMetaGroupeCriteria that = (DateMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(every, that.every) &&
            Objects.equals(hour, that.hour) &&
            Objects.equals(minutes, that.minutes) &&
            Objects.equals(groupeId, that.groupeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateDebut,
        dateFin,
        every,
        hour,
        minutes,
        groupeId
        );
    }

    @Override
    public String toString() {
        return "DateMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (every != null ? "every=" + every + ", " : "") +
                (hour != null ? "hour=" + hour + ", " : "") +
                (minutes != null ? "minutes=" + minutes + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
            "}";
    }

}
