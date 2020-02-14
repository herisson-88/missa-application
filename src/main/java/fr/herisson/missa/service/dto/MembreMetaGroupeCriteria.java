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
 * Criteria class for the {@link fr.herisson.missa.domain.MembreMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.MembreMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /membre-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MembreMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter validated;

    private ZonedDateTimeFilter dateValidation;

    private BooleanFilter admin;

    private BooleanFilter mailingList;

    private LongFilter groupeMembreId;

    private LongFilter valideParId;

    private LongFilter missaUserId;

    public MembreMetaGroupeCriteria() {
    }

    public MembreMetaGroupeCriteria(MembreMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.validated = other.validated == null ? null : other.validated.copy();
        this.dateValidation = other.dateValidation == null ? null : other.dateValidation.copy();
        this.admin = other.admin == null ? null : other.admin.copy();
        this.mailingList = other.mailingList == null ? null : other.mailingList.copy();
        this.groupeMembreId = other.groupeMembreId == null ? null : other.groupeMembreId.copy();
        this.valideParId = other.valideParId == null ? null : other.valideParId.copy();
        this.missaUserId = other.missaUserId == null ? null : other.missaUserId.copy();
    }

    @Override
    public MembreMetaGroupeCriteria copy() {
        return new MembreMetaGroupeCriteria(this);
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

    public BooleanFilter getAdmin() {
        return admin;
    }

    public void setAdmin(BooleanFilter admin) {
        this.admin = admin;
    }

    public BooleanFilter getMailingList() {
        return mailingList;
    }

    public void setMailingList(BooleanFilter mailingList) {
        this.mailingList = mailingList;
    }

    public LongFilter getGroupeMembreId() {
        return groupeMembreId;
    }

    public void setGroupeMembreId(LongFilter groupeMembreId) {
        this.groupeMembreId = groupeMembreId;
    }

    public LongFilter getValideParId() {
        return valideParId;
    }

    public void setValideParId(LongFilter valideParId) {
        this.valideParId = valideParId;
    }

    public LongFilter getMissaUserId() {
        return missaUserId;
    }

    public void setMissaUserId(LongFilter missaUserId) {
        this.missaUserId = missaUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MembreMetaGroupeCriteria that = (MembreMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(validated, that.validated) &&
            Objects.equals(dateValidation, that.dateValidation) &&
            Objects.equals(admin, that.admin) &&
            Objects.equals(mailingList, that.mailingList) &&
            Objects.equals(groupeMembreId, that.groupeMembreId) &&
            Objects.equals(valideParId, that.valideParId) &&
            Objects.equals(missaUserId, that.missaUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        validated,
        dateValidation,
        admin,
        mailingList,
        groupeMembreId,
        valideParId,
        missaUserId
        );
    }

    @Override
    public String toString() {
        return "MembreMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (validated != null ? "validated=" + validated + ", " : "") +
                (dateValidation != null ? "dateValidation=" + dateValidation + ", " : "") +
                (admin != null ? "admin=" + admin + ", " : "") +
                (mailingList != null ? "mailingList=" + mailingList + ", " : "") +
                (groupeMembreId != null ? "groupeMembreId=" + groupeMembreId + ", " : "") +
                (valideParId != null ? "valideParId=" + valideParId + ", " : "") +
                (missaUserId != null ? "missaUserId=" + missaUserId + ", " : "") +
            "}";
    }

}
