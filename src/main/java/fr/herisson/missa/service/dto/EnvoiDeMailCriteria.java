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
 * Criteria class for the {@link fr.herisson.missa.domain.EnvoiDeMail} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.EnvoiDeMailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /envoi-de-mails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnvoiDeMailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter dateEnvoi;

    private StringFilter titre;

    private IntegerFilter nbDestinataire;

    private BooleanFilter sended;

    private LongFilter groupePartageParMailId;

    private LongFilter adminId;

    private LongFilter groupeDuMailId;

    public EnvoiDeMailCriteria() {
    }

    public EnvoiDeMailCriteria(EnvoiDeMailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateEnvoi = other.dateEnvoi == null ? null : other.dateEnvoi.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.nbDestinataire = other.nbDestinataire == null ? null : other.nbDestinataire.copy();
        this.sended = other.sended == null ? null : other.sended.copy();
        this.groupePartageParMailId = other.groupePartageParMailId == null ? null : other.groupePartageParMailId.copy();
        this.adminId = other.adminId == null ? null : other.adminId.copy();
        this.groupeDuMailId = other.groupeDuMailId == null ? null : other.groupeDuMailId.copy();
    }

    @Override
    public EnvoiDeMailCriteria copy() {
        return new EnvoiDeMailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(ZonedDateTimeFilter dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public IntegerFilter getNbDestinataire() {
        return nbDestinataire;
    }

    public void setNbDestinataire(IntegerFilter nbDestinataire) {
        this.nbDestinataire = nbDestinataire;
    }

    public BooleanFilter getSended() {
        return sended;
    }

    public void setSended(BooleanFilter sended) {
        this.sended = sended;
    }

    public LongFilter getGroupePartageParMailId() {
        return groupePartageParMailId;
    }

    public void setGroupePartageParMailId(LongFilter groupePartageParMailId) {
        this.groupePartageParMailId = groupePartageParMailId;
    }

    public LongFilter getAdminId() {
        return adminId;
    }

    public void setAdminId(LongFilter adminId) {
        this.adminId = adminId;
    }

    public LongFilter getGroupeDuMailId() {
        return groupeDuMailId;
    }

    public void setGroupeDuMailId(LongFilter groupeDuMailId) {
        this.groupeDuMailId = groupeDuMailId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnvoiDeMailCriteria that = (EnvoiDeMailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateEnvoi, that.dateEnvoi) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(nbDestinataire, that.nbDestinataire) &&
            Objects.equals(sended, that.sended) &&
            Objects.equals(groupePartageParMailId, that.groupePartageParMailId) &&
            Objects.equals(adminId, that.adminId) &&
            Objects.equals(groupeDuMailId, that.groupeDuMailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateEnvoi,
        titre,
        nbDestinataire,
        sended,
        groupePartageParMailId,
        adminId,
        groupeDuMailId
        );
    }

    @Override
    public String toString() {
        return "EnvoiDeMailCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateEnvoi != null ? "dateEnvoi=" + dateEnvoi + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (nbDestinataire != null ? "nbDestinataire=" + nbDestinataire + ", " : "") +
                (sended != null ? "sended=" + sended + ", " : "") +
                (groupePartageParMailId != null ? "groupePartageParMailId=" + groupePartageParMailId + ", " : "") +
                (adminId != null ? "adminId=" + adminId + ", " : "") +
                (groupeDuMailId != null ? "groupeDuMailId=" + groupeDuMailId + ", " : "") +
            "}";
    }

}
