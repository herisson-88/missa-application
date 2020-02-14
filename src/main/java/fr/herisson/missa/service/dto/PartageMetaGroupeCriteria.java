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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.PartageMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.PartageMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /partage-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartageMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter validated;

    private ZonedDateTimeFilter dateValidation;

    private LongFilter metaGroupePartageId;

    private LongFilter metaGroupeDestinatairesId;

    private LongFilter auteurPartageId;

    private LongFilter validateurDuPartageId;

    public PartageMetaGroupeCriteria() {
    }

    public PartageMetaGroupeCriteria(PartageMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.validated = other.validated == null ? null : other.validated.copy();
        this.dateValidation = other.dateValidation == null ? null : other.dateValidation.copy();
        this.metaGroupePartageId = other.metaGroupePartageId == null ? null : other.metaGroupePartageId.copy();
        this.metaGroupeDestinatairesId = other.metaGroupeDestinatairesId == null ? null : other.metaGroupeDestinatairesId.copy();
        this.auteurPartageId = other.auteurPartageId == null ? null : other.auteurPartageId.copy();
        this.validateurDuPartageId = other.validateurDuPartageId == null ? null : other.validateurDuPartageId.copy();
    }

    @Override
    public PartageMetaGroupeCriteria copy() {
        return new PartageMetaGroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getValidated() {
        return validated;
    }

    public void setValidated(BooleanFilter validated) {
        this.validated = validated;
    }

    public ZonedDateTimeFilter getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(ZonedDateTimeFilter dateValidation) {
        this.dateValidation = dateValidation;
    }

    public LongFilter getMetaGroupePartageId() {
        return metaGroupePartageId;
    }

    public void setMetaGroupePartageId(LongFilter metaGroupePartageId) {
        this.metaGroupePartageId = metaGroupePartageId;
    }

    public LongFilter getMetaGroupeDestinatairesId() {
        return metaGroupeDestinatairesId;
    }

    public void setMetaGroupeDestinatairesId(LongFilter metaGroupeDestinatairesId) {
        this.metaGroupeDestinatairesId = metaGroupeDestinatairesId;
    }

    public LongFilter getAuteurPartageId() {
        return auteurPartageId;
    }

    public void setAuteurPartageId(LongFilter auteurPartageId) {
        this.auteurPartageId = auteurPartageId;
    }

    public LongFilter getValidateurDuPartageId() {
        return validateurDuPartageId;
    }

    public void setValidateurDuPartageId(LongFilter validateurDuPartageId) {
        this.validateurDuPartageId = validateurDuPartageId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartageMetaGroupeCriteria that = (PartageMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(validated, that.validated) &&
            Objects.equals(dateValidation, that.dateValidation) &&
            Objects.equals(metaGroupePartageId, that.metaGroupePartageId) &&
            Objects.equals(metaGroupeDestinatairesId, that.metaGroupeDestinatairesId) &&
            Objects.equals(auteurPartageId, that.auteurPartageId) &&
            Objects.equals(validateurDuPartageId, that.validateurDuPartageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        validated,
        dateValidation,
        metaGroupePartageId,
        metaGroupeDestinatairesId,
        auteurPartageId,
        validateurDuPartageId
        );
    }

    @Override
    public String toString() {
        return "PartageMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (validated != null ? "validated=" + validated + ", " : "") +
                (dateValidation != null ? "dateValidation=" + dateValidation + ", " : "") +
                (metaGroupePartageId != null ? "metaGroupePartageId=" + metaGroupePartageId + ", " : "") +
                (metaGroupeDestinatairesId != null ? "metaGroupeDestinatairesId=" + metaGroupeDestinatairesId + ", " : "") +
                (auteurPartageId != null ? "auteurPartageId=" + auteurPartageId + ", " : "") +
                (validateurDuPartageId != null ? "validateurDuPartageId=" + validateurDuPartageId + ", " : "") +
            "}";
    }

}
