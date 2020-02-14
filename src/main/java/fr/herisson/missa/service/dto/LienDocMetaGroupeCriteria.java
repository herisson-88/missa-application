package fr.herisson.missa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.herisson.missa.domain.enumeration.TypeDoc;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.LienDocMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.LienDocMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lien-doc-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LienDocMetaGroupeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TypeDoc
     */
    public static class TypeDocFilter extends Filter<TypeDoc> {

        public TypeDocFilter() {
        }

        public TypeDocFilter(TypeDocFilter filter) {
            super(filter);
        }

        @Override
        public TypeDocFilter copy() {
            return new TypeDocFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private TypeDocFilter typeDuDoc;

    private LongFilter groupeId;

    private LongFilter typeId;

    public LienDocMetaGroupeCriteria() {
    }

    public LienDocMetaGroupeCriteria(LienDocMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.typeDuDoc = other.typeDuDoc == null ? null : other.typeDuDoc.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
    }

    @Override
    public LienDocMetaGroupeCriteria copy() {
        return new LienDocMetaGroupeCriteria(this);
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

    public TypeDocFilter getTypeDuDoc() {
        return typeDuDoc;
    }

    public void setTypeDuDoc(TypeDocFilter typeDuDoc) {
        this.typeDuDoc = typeDuDoc;
    }

    public LongFilter getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(LongFilter groupeId) {
        this.groupeId = groupeId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LienDocMetaGroupeCriteria that = (LienDocMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(typeDuDoc, that.typeDuDoc) &&
            Objects.equals(groupeId, that.groupeId) &&
            Objects.equals(typeId, that.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        typeDuDoc,
        groupeId,
        typeId
        );
    }

    @Override
    public String toString() {
        return "LienDocMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (typeDuDoc != null ? "typeDuDoc=" + typeDuDoc + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
            "}";
    }

}
