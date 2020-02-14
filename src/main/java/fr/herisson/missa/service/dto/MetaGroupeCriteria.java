package fr.herisson.missa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.herisson.missa.domain.enumeration.MembreDiffusion;
import fr.herisson.missa.domain.enumeration.Visibilite;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.MetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.MetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MetaGroupeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering MembreDiffusion
     */
    public static class MembreDiffusionFilter extends Filter<MembreDiffusion> {

        public MembreDiffusionFilter() {
        }

        public MembreDiffusionFilter(MembreDiffusionFilter filter) {
            super(filter);
        }

        @Override
        public MembreDiffusionFilter copy() {
            return new MembreDiffusionFilter(this);
        }

    }
    /**
     * Class for filtering Visibilite
     */
    public static class VisibiliteFilter extends Filter<Visibilite> {

        public VisibiliteFilter() {
        }

        public VisibiliteFilter(VisibiliteFilter filter) {
            super(filter);
        }

        @Override
        public VisibiliteFilter copy() {
            return new VisibiliteFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private BooleanFilter autoriteValidation;

    private MembreDiffusionFilter membreParent;

    private VisibiliteFilter visibilite;

    private BooleanFilter droitEnvoiDeMail;

    private BooleanFilter demandeDiffusionVerticale;

    private BooleanFilter messagerieModeree;

    private BooleanFilter groupeFinal;

    private ZonedDateTimeFilter dateValidation;

    private LongFilter membresId;

    private LongFilter datesId;

    private LongFilter lieuId;

    private LongFilter docsId;

    private LongFilter coordonneesOrganisateursId;

    private LongFilter sousGroupesId;

    private LongFilter messagesDuGroupeId;

    private LongFilter mailsId;

    private LongFilter partagesVersId;

    private LongFilter partagesRecusId;

    private LongFilter parentId;

    private LongFilter typeId;

    private LongFilter reseauId;

    private LongFilter messageMailReferentId;

    public MetaGroupeCriteria() {
    }

    public MetaGroupeCriteria(MetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.autoriteValidation = other.autoriteValidation == null ? null : other.autoriteValidation.copy();
        this.membreParent = other.membreParent == null ? null : other.membreParent.copy();
        this.visibilite = other.visibilite == null ? null : other.visibilite.copy();
        this.droitEnvoiDeMail = other.droitEnvoiDeMail == null ? null : other.droitEnvoiDeMail.copy();
        this.demandeDiffusionVerticale = other.demandeDiffusionVerticale == null ? null : other.demandeDiffusionVerticale.copy();
        this.messagerieModeree = other.messagerieModeree == null ? null : other.messagerieModeree.copy();
        this.groupeFinal = other.groupeFinal == null ? null : other.groupeFinal.copy();
        this.dateValidation = other.dateValidation == null ? null : other.dateValidation.copy();
        this.membresId = other.membresId == null ? null : other.membresId.copy();
        this.datesId = other.datesId == null ? null : other.datesId.copy();
        this.lieuId = other.lieuId == null ? null : other.lieuId.copy();
        this.docsId = other.docsId == null ? null : other.docsId.copy();
        this.coordonneesOrganisateursId = other.coordonneesOrganisateursId == null ? null : other.coordonneesOrganisateursId.copy();
        this.sousGroupesId = other.sousGroupesId == null ? null : other.sousGroupesId.copy();
        this.messagesDuGroupeId = other.messagesDuGroupeId == null ? null : other.messagesDuGroupeId.copy();
        this.mailsId = other.mailsId == null ? null : other.mailsId.copy();
        this.partagesVersId = other.partagesVersId == null ? null : other.partagesVersId.copy();
        this.partagesRecusId = other.partagesRecusId == null ? null : other.partagesRecusId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.typeId = other.typeId == null ? null : other.typeId.copy();
        this.reseauId = other.reseauId == null ? null : other.reseauId.copy();
        this.messageMailReferentId = other.messageMailReferentId == null ? null : other.messageMailReferentId.copy();
    }

    @Override
    public MetaGroupeCriteria copy() {
        return new MetaGroupeCriteria(this);
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

    public BooleanFilter getAutoriteValidation() {
        return autoriteValidation;
    }

    public void setAutoriteValidation(BooleanFilter autoriteValidation) {
        this.autoriteValidation = autoriteValidation;
    }

    public MembreDiffusionFilter getMembreParent() {
        return membreParent;
    }

    public void setMembreParent(MembreDiffusionFilter membreParent) {
        this.membreParent = membreParent;
    }

    public VisibiliteFilter getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(VisibiliteFilter visibilite) {
        this.visibilite = visibilite;
    }

    public BooleanFilter getDroitEnvoiDeMail() {
        return droitEnvoiDeMail;
    }

    public void setDroitEnvoiDeMail(BooleanFilter droitEnvoiDeMail) {
        this.droitEnvoiDeMail = droitEnvoiDeMail;
    }

    public BooleanFilter getDemandeDiffusionVerticale() {
        return demandeDiffusionVerticale;
    }

    public void setDemandeDiffusionVerticale(BooleanFilter demandeDiffusionVerticale) {
        this.demandeDiffusionVerticale = demandeDiffusionVerticale;
    }

    public BooleanFilter getMessagerieModeree() {
        return messagerieModeree;
    }

    public void setMessagerieModeree(BooleanFilter messagerieModeree) {
        this.messagerieModeree = messagerieModeree;
    }

    public BooleanFilter getGroupeFinal() {
        return groupeFinal;
    }

    public void setGroupeFinal(BooleanFilter groupeFinal) {
        this.groupeFinal = groupeFinal;
    }

    public ZonedDateTimeFilter getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(ZonedDateTimeFilter dateValidation) {
        this.dateValidation = dateValidation;
    }

    public LongFilter getMembresId() {
        return membresId;
    }

    public void setMembresId(LongFilter membresId) {
        this.membresId = membresId;
    }

    public LongFilter getDatesId() {
        return datesId;
    }

    public void setDatesId(LongFilter datesId) {
        this.datesId = datesId;
    }

    public LongFilter getLieuId() {
        return lieuId;
    }

    public void setLieuId(LongFilter lieuId) {
        this.lieuId = lieuId;
    }

    public LongFilter getDocsId() {
        return docsId;
    }

    public void setDocsId(LongFilter docsId) {
        this.docsId = docsId;
    }

    public LongFilter getCoordonneesOrganisateursId() {
        return coordonneesOrganisateursId;
    }

    public void setCoordonneesOrganisateursId(LongFilter coordonneesOrganisateursId) {
        this.coordonneesOrganisateursId = coordonneesOrganisateursId;
    }

    public LongFilter getSousGroupesId() {
        return sousGroupesId;
    }

    public void setSousGroupesId(LongFilter sousGroupesId) {
        this.sousGroupesId = sousGroupesId;
    }

    public LongFilter getMessagesDuGroupeId() {
        return messagesDuGroupeId;
    }

    public void setMessagesDuGroupeId(LongFilter messagesDuGroupeId) {
        this.messagesDuGroupeId = messagesDuGroupeId;
    }

    public LongFilter getMailsId() {
        return mailsId;
    }

    public void setMailsId(LongFilter mailsId) {
        this.mailsId = mailsId;
    }

    public LongFilter getPartagesVersId() {
        return partagesVersId;
    }

    public void setPartagesVersId(LongFilter partagesVersId) {
        this.partagesVersId = partagesVersId;
    }

    public LongFilter getPartagesRecusId() {
        return partagesRecusId;
    }

    public void setPartagesRecusId(LongFilter partagesRecusId) {
        this.partagesRecusId = partagesRecusId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getTypeId() {
        return typeId;
    }

    public void setTypeId(LongFilter typeId) {
        this.typeId = typeId;
    }

    public LongFilter getReseauId() {
        return reseauId;
    }

    public void setReseauId(LongFilter reseauId) {
        this.reseauId = reseauId;
    }

    public LongFilter getMessageMailReferentId() {
        return messageMailReferentId;
    }

    public void setMessageMailReferentId(LongFilter messageMailReferentId) {
        this.messageMailReferentId = messageMailReferentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MetaGroupeCriteria that = (MetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(autoriteValidation, that.autoriteValidation) &&
            Objects.equals(membreParent, that.membreParent) &&
            Objects.equals(visibilite, that.visibilite) &&
            Objects.equals(droitEnvoiDeMail, that.droitEnvoiDeMail) &&
            Objects.equals(demandeDiffusionVerticale, that.demandeDiffusionVerticale) &&
            Objects.equals(messagerieModeree, that.messagerieModeree) &&
            Objects.equals(groupeFinal, that.groupeFinal) &&
            Objects.equals(dateValidation, that.dateValidation) &&
            Objects.equals(membresId, that.membresId) &&
            Objects.equals(datesId, that.datesId) &&
            Objects.equals(lieuId, that.lieuId) &&
            Objects.equals(docsId, that.docsId) &&
            Objects.equals(coordonneesOrganisateursId, that.coordonneesOrganisateursId) &&
            Objects.equals(sousGroupesId, that.sousGroupesId) &&
            Objects.equals(messagesDuGroupeId, that.messagesDuGroupeId) &&
            Objects.equals(mailsId, that.mailsId) &&
            Objects.equals(partagesVersId, that.partagesVersId) &&
            Objects.equals(partagesRecusId, that.partagesRecusId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(typeId, that.typeId) &&
            Objects.equals(reseauId, that.reseauId) &&
            Objects.equals(messageMailReferentId, that.messageMailReferentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        autoriteValidation,
        membreParent,
        visibilite,
        droitEnvoiDeMail,
        demandeDiffusionVerticale,
        messagerieModeree,
        groupeFinal,
        dateValidation,
        membresId,
        datesId,
        lieuId,
        docsId,
        coordonneesOrganisateursId,
        sousGroupesId,
        messagesDuGroupeId,
        mailsId,
        partagesVersId,
        partagesRecusId,
        parentId,
        typeId,
        reseauId,
        messageMailReferentId
        );
    }

    @Override
    public String toString() {
        return "MetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (autoriteValidation != null ? "autoriteValidation=" + autoriteValidation + ", " : "") +
                (membreParent != null ? "membreParent=" + membreParent + ", " : "") +
                (visibilite != null ? "visibilite=" + visibilite + ", " : "") +
                (droitEnvoiDeMail != null ? "droitEnvoiDeMail=" + droitEnvoiDeMail + ", " : "") +
                (demandeDiffusionVerticale != null ? "demandeDiffusionVerticale=" + demandeDiffusionVerticale + ", " : "") +
                (messagerieModeree != null ? "messagerieModeree=" + messagerieModeree + ", " : "") +
                (groupeFinal != null ? "groupeFinal=" + groupeFinal + ", " : "") +
                (dateValidation != null ? "dateValidation=" + dateValidation + ", " : "") +
                (membresId != null ? "membresId=" + membresId + ", " : "") +
                (datesId != null ? "datesId=" + datesId + ", " : "") +
                (lieuId != null ? "lieuId=" + lieuId + ", " : "") +
                (docsId != null ? "docsId=" + docsId + ", " : "") +
                (coordonneesOrganisateursId != null ? "coordonneesOrganisateursId=" + coordonneesOrganisateursId + ", " : "") +
                (sousGroupesId != null ? "sousGroupesId=" + sousGroupesId + ", " : "") +
                (messagesDuGroupeId != null ? "messagesDuGroupeId=" + messagesDuGroupeId + ", " : "") +
                (mailsId != null ? "mailsId=" + mailsId + ", " : "") +
                (partagesVersId != null ? "partagesVersId=" + partagesVersId + ", " : "") +
                (partagesRecusId != null ? "partagesRecusId=" + partagesRecusId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (typeId != null ? "typeId=" + typeId + ", " : "") +
                (reseauId != null ? "reseauId=" + reseauId + ", " : "") +
                (messageMailReferentId != null ? "messageMailReferentId=" + messageMailReferentId + ", " : "") +
            "}";
    }

}
