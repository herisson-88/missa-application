package fr.herisson.missa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.Reseau} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.ReseauResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reseaus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReseauCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private LongFilter groupesId;

    private LongFilter typesId;

    public ReseauCriteria() {
    }

    public ReseauCriteria(ReseauCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.groupesId = other.groupesId == null ? null : other.groupesId.copy();
        this.typesId = other.typesId == null ? null : other.typesId.copy();
    }

    @Override
    public ReseauCriteria copy() {
        return new ReseauCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getGroupesId() {
        return groupesId;
    }

    public void setGroupesId(LongFilter groupesId) {
        this.groupesId = groupesId;
    }

    public LongFilter getTypesId() {
        return typesId;
    }

    public void setTypesId(LongFilter typesId) {
        this.typesId = typesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReseauCriteria that = (ReseauCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(groupesId, that.groupesId) &&
            Objects.equals(typesId, that.typesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        groupesId,
        typesId
        );
    }

    @Override
    public String toString() {
        return "ReseauCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (groupesId != null ? "groupesId=" + groupesId + ", " : "") +
                (typesId != null ? "typesId=" + typesId + ", " : "") +
            "}";
    }

}
