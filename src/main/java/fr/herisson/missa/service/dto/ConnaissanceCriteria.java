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
 * Criteria class for the {@link fr.herisson.missa.domain.Connaissance} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.ConnaissanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /connaissances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConnaissanceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter mail;

    private StringFilter idFaceBook;

    private BooleanFilter parraine;

    private LongFilter connuParId;

    public ConnaissanceCriteria() {
    }

    public ConnaissanceCriteria(ConnaissanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.idFaceBook = other.idFaceBook == null ? null : other.idFaceBook.copy();
        this.parraine = other.parraine == null ? null : other.parraine.copy();
        this.connuParId = other.connuParId == null ? null : other.connuParId.copy();
    }

    @Override
    public ConnaissanceCriteria copy() {
        return new ConnaissanceCriteria(this);
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

    public StringFilter getIdFaceBook() {
        return idFaceBook;
    }

    public void setIdFaceBook(StringFilter idFaceBook) {
        this.idFaceBook = idFaceBook;
    }

    public BooleanFilter getParraine() {
        return parraine;
    }

    public void setParraine(BooleanFilter parraine) {
        this.parraine = parraine;
    }

    public LongFilter getConnuParId() {
        return connuParId;
    }

    public void setConnuParId(LongFilter connuParId) {
        this.connuParId = connuParId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConnaissanceCriteria that = (ConnaissanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(idFaceBook, that.idFaceBook) &&
            Objects.equals(parraine, that.parraine) &&
            Objects.equals(connuParId, that.connuParId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        prenom,
        mail,
        idFaceBook,
        parraine,
        connuParId
        );
    }

    @Override
    public String toString() {
        return "ConnaissanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (idFaceBook != null ? "idFaceBook=" + idFaceBook + ", " : "") +
                (parraine != null ? "parraine=" + parraine + ", " : "") +
                (connuParId != null ? "connuParId=" + connuParId + ", " : "") +
            "}";
    }

}
