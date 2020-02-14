package fr.herisson.missa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import fr.herisson.missa.domain.enumeration.EtatUser;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link fr.herisson.missa.domain.MissaUser} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.MissaUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /missa-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MissaUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EtatUser
     */
    public static class EtatUserFilter extends Filter<EtatUser> {

        public EtatUserFilter() {
        }

        public EtatUserFilter(EtatUserFilter filter) {
            super(filter);
        }

        @Override
        public EtatUserFilter copy() {
            return new EtatUserFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codePostal;

    private StringFilter initiales;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter mail;

    private EtatUserFilter etat;

    private StringFilter userId;

    private LongFilter connaisId;

    private LongFilter membreValidesId;

    private LongFilter membresId;

    private LongFilter messagesDeMoiId;

    private LongFilter mailsId;

    private LongFilter demandesPartagesId;

    private LongFilter aValidePartagesId;

    public MissaUserCriteria() {
    }

    public MissaUserCriteria(MissaUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.initiales = other.initiales == null ? null : other.initiales.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.connaisId = other.connaisId == null ? null : other.connaisId.copy();
        this.membreValidesId = other.membreValidesId == null ? null : other.membreValidesId.copy();
        this.membresId = other.membresId == null ? null : other.membresId.copy();
        this.messagesDeMoiId = other.messagesDeMoiId == null ? null : other.messagesDeMoiId.copy();
        this.mailsId = other.mailsId == null ? null : other.mailsId.copy();
        this.demandesPartagesId = other.demandesPartagesId == null ? null : other.demandesPartagesId.copy();
        this.aValidePartagesId = other.aValidePartagesId == null ? null : other.aValidePartagesId.copy();
    }

    @Override
    public MissaUserCriteria copy() {
        return new MissaUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(StringFilter codePostal) {
        this.codePostal = codePostal;
    }

    public StringFilter getInitiales() {
        return initiales;
    }

    public void setInitiales(StringFilter initiales) {
        this.initiales = initiales;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public EtatUserFilter getEtat() {
        return etat;
    }

    public void setEtat(EtatUserFilter etat) {
        this.etat = etat;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public LongFilter getConnaisId() {
        return connaisId;
    }

    public void setConnaisId(LongFilter connaisId) {
        this.connaisId = connaisId;
    }

    public LongFilter getMembreValidesId() {
        return membreValidesId;
    }

    public void setMembreValidesId(LongFilter membreValidesId) {
        this.membreValidesId = membreValidesId;
    }

    public LongFilter getMembresId() {
        return membresId;
    }

    public void setMembresId(LongFilter membresId) {
        this.membresId = membresId;
    }

    public LongFilter getMessagesDeMoiId() {
        return messagesDeMoiId;
    }

    public void setMessagesDeMoiId(LongFilter messagesDeMoiId) {
        this.messagesDeMoiId = messagesDeMoiId;
    }

    public LongFilter getMailsId() {
        return mailsId;
    }

    public void setMailsId(LongFilter mailsId) {
        this.mailsId = mailsId;
    }

    public LongFilter getDemandesPartagesId() {
        return demandesPartagesId;
    }

    public void setDemandesPartagesId(LongFilter demandesPartagesId) {
        this.demandesPartagesId = demandesPartagesId;
    }

    public LongFilter getAValidePartagesId() {
        return aValidePartagesId;
    }

    public void setAValidePartagesId(LongFilter aValidePartagesId) {
        this.aValidePartagesId = aValidePartagesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MissaUserCriteria that = (MissaUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(initiales, that.initiales) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(connaisId, that.connaisId) &&
            Objects.equals(membreValidesId, that.membreValidesId) &&
            Objects.equals(membresId, that.membresId) &&
            Objects.equals(messagesDeMoiId, that.messagesDeMoiId) &&
            Objects.equals(mailsId, that.mailsId) &&
            Objects.equals(demandesPartagesId, that.demandesPartagesId) &&
            Objects.equals(aValidePartagesId, that.aValidePartagesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codePostal,
        initiales,
        nom,
        prenom,
        mail,
        etat,
        userId,
        connaisId,
        membreValidesId,
        membresId,
        messagesDeMoiId,
        mailsId,
        demandesPartagesId,
        aValidePartagesId
        );
    }

    @Override
    public String toString() {
        return "MissaUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
                (initiales != null ? "initiales=" + initiales + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (connaisId != null ? "connaisId=" + connaisId + ", " : "") +
                (membreValidesId != null ? "membreValidesId=" + membreValidesId + ", " : "") +
                (membresId != null ? "membresId=" + membresId + ", " : "") +
                (messagesDeMoiId != null ? "messagesDeMoiId=" + messagesDeMoiId + ", " : "") +
                (mailsId != null ? "mailsId=" + mailsId + ", " : "") +
                (demandesPartagesId != null ? "demandesPartagesId=" + demandesPartagesId + ", " : "") +
                (aValidePartagesId != null ? "aValidePartagesId=" + aValidePartagesId + ", " : "") +
            "}";
    }

}
