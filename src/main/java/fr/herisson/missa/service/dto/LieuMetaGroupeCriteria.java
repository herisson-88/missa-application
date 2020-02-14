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
 * Criteria class for the {@link fr.herisson.missa.domain.LieuMetaGroupe} entity. This class is used
 * in {@link fr.herisson.missa.web.rest.LieuMetaGroupeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /lieu-meta-groupes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LieuMetaGroupeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter adresse;

    private StringFilter codePostal;

    private StringFilter ville;

    private DoubleFilter lat;

    private DoubleFilter lon;

    private StringFilter departementGroupe;

    private LongFilter groupeId;

    public LieuMetaGroupeCriteria() {
    }

    public LieuMetaGroupeCriteria(LieuMetaGroupeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.lon = other.lon == null ? null : other.lon.copy();
        this.departementGroupe = other.departementGroupe == null ? null : other.departementGroupe.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
    }

    @Override
    public LieuMetaGroupeCriteria copy() {
        return new LieuMetaGroupeCriteria(this);
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

    public DoubleFilter getLat() {
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public DoubleFilter getLon() {
        return lon;
    }

    public void setLon(DoubleFilter lon) {
        this.lon = lon;
    }

    public StringFilter getDepartementGroupe() {
        return departementGroupe;
    }

    public void setDepartementGroupe(StringFilter departementGroupe) {
        this.departementGroupe = departementGroupe;
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
        final LieuMetaGroupeCriteria that = (LieuMetaGroupeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(lon, that.lon) &&
            Objects.equals(departementGroupe, that.departementGroupe) &&
            Objects.equals(groupeId, that.groupeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        adresse,
        codePostal,
        ville,
        lat,
        lon,
        departementGroupe,
        groupeId
        );
    }

    @Override
    public String toString() {
        return "LieuMetaGroupeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (lon != null ? "lon=" + lon + ", " : "") +
                (departementGroupe != null ? "departementGroupe=" + departementGroupe + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
            "}";
    }

}
