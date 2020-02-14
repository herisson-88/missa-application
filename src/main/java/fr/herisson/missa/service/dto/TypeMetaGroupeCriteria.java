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
 * Criteria class for the {@link fr.herisson.missa.domain.TypeMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.TypeMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /type-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter typeDuGroupe;

    private StringFilter iconeFa;

    private IntegerFilter ordreMail;

    private LongFilter defaultDocsId;

    private LongFilter groupesId;

    private LongFilter reseauId;

    public TypeMetaGroupeCriteria() {
    }

    public TypeMetaGroupeCriteria(TypeMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.typeDuGroupe = other.typeDuGroupe == null ? null : other.typeDuGroupe.copy();
        this.iconeFa = other.iconeFa == null ? null : other.iconeFa.copy();
        this.ordreMail = other.ordreMail == null ? null : other.ordreMail.copy();
        this.defaultDocsId = other.defaultDocsId == null ? null : other.defaultDocsId.copy();
        this.groupesId = other.groupesId == null ? null : other.groupesId.copy();
        this.reseauId = other.reseauId == null ? null : other.reseauId.copy();
    }

    @Override
    public TypeMetaGroupeCriteria copy() {
        return new TypeMetaGroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTypeDuGroupe() {
        return typeDuGroupe;
    }

    public void setTypeDuGroupe(StringFilter typeDuGroupe) {
        this.typeDuGroupe = typeDuGroupe;
    }

    public StringFilter getIconeFa() {
        return iconeFa;
    }

    public void setIconeFa(StringFilter iconeFa) {
        this.iconeFa = iconeFa;
    }

    public IntegerFilter getOrdreMail() {
        return ordreMail;
    }

    public void setOrdreMail(IntegerFilter ordreMail) {
        this.ordreMail = ordreMail;
    }

    public LongFilter getDefaultDocsId() {
        return defaultDocsId;
    }

    public void setDefaultDocsId(LongFilter defaultDocsId) {
        this.defaultDocsId = defaultDocsId;
    }

    public LongFilter getGroupesId() {
        return groupesId;
    }

    public void setGroupesId(LongFilter groupesId) {
        this.groupesId = groupesId;
    }

    public LongFilter getReseauId() {
        return reseauId;
    }

    public void setReseauId(LongFilter reseauId) {
        this.reseauId = reseauId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeMetaGroupeCriteria that = (TypeMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(typeDuGroupe, that.typeDuGroupe) &&
            Objects.equals(iconeFa, that.iconeFa) &&
            Objects.equals(ordreMail, that.ordreMail) &&
            Objects.equals(defaultDocsId, that.defaultDocsId) &&
            Objects.equals(groupesId, that.groupesId) &&
            Objects.equals(reseauId, that.reseauId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        typeDuGroupe,
        iconeFa,
        ordreMail,
        defaultDocsId,
        groupesId,
        reseauId
        );
    }

    @Override
    public String toString() {
        return "TypeMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeDuGroupe != null ? "typeDuGroupe=" + typeDuGroupe + ", " : "") +
                (iconeFa != null ? "iconeFa=" + iconeFa + ", " : "") +
                (ordreMail != null ? "ordreMail=" + ordreMail + ", " : "") +
                (defaultDocsId != null ? "defaultDocsId=" + defaultDocsId + ", " : "") +
                (groupesId != null ? "groupesId=" + groupesId + ", " : "") +
                (reseauId != null ? "reseauId=" + reseauId + ", " : "") +
            "}";
    }

}
