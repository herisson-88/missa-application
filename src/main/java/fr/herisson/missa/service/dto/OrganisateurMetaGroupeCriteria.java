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
 * Criteria class for the {@link fr.herisson.missa.domain.OrganisateurMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.OrganisateurMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organisateur-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrganisateurMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter site;

    private StringFilter nom;

    private StringFilter prenom;

    private StringFilter telephone;

    private StringFilter mail;

    private StringFilter adresse;

    private StringFilter codePostal;

    private StringFilter ville;

    private LongFilter groupeId;

    public OrganisateurMetaGroupeCriteria() {
    }

    public OrganisateurMetaGroupeCriteria(OrganisateurMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.site = other.site == null ? null : other.site.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
    }

    @Override
    public OrganisateurMetaGroupeCriteria copy() {
        return new OrganisateurMetaGroupeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSite() {
        return site;
    }

    public void setSite(StringFilter site) {
        this.site = site;
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

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(StringFilter codePostal) {
        this.codePostal = codePostal;
    }

    public StringFilter getVille() {
        return ville;
    }

    public void setVille(StringFilter ville) {
        this.ville = ville;
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
        final OrganisateurMetaGroupeCriteria that = (OrganisateurMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(site, that.site) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(groupeId, that.groupeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        site,
        nom,
        prenom,
        telephone,
        mail,
        adresse,
        codePostal,
        ville,
        groupeId
        );
    }

    @Override
    public String toString() {
        return "OrganisateurMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (site != null ? "site=" + site + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
            "}";
    }

}
