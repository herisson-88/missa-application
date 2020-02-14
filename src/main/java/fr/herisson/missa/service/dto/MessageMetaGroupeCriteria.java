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
 * Criteria class for the {@link fr.herisson.missa.domain.MessageMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.MessageMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /message-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titre;

    private BooleanFilter publique;

    private LongFilter auteurId;

    private LongFilter groupeId;

    public MessageMetaGroupeCriteria() {
    }

    public MessageMetaGroupeCriteria(MessageMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.titre = other.titre == null ? null : other.titre.copy();
        this.publique = other.publique == null ? null : other.publique.copy();
        this.auteurId = other.auteurId == null ? null : other.auteurId.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
    }

    @Override
    public MessageMetaGroupeCriteria copy() {
        return new MessageMetaGroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public BooleanFilter getPublique() {
        return publique;
    }

    public void setPublique(BooleanFilter publique) {
        this.publique = publique;
    }

    public LongFilter getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(LongFilter auteurId) {
        this.auteurId = auteurId;
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
        final MessageMetaGroupeCriteria that = (MessageMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(publique, that.publique) &&
            Objects.equals(auteurId, that.auteurId) &&
            Objects.equals(groupeId, that.groupeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titre,
        publique,
        auteurId,
        groupeId
        );
    }

    @Override
    public String toString() {
        return "MessageMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (publique != null ? "publique=" + publique + ", " : "") +
                (auteurId != null ? "auteurId=" + auteurId + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
            "}";
    }

}
