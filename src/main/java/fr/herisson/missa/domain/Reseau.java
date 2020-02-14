package fr.herisson.missa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reseau.
 */
@Entity
@Table(name = "reseau")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reseau")
public class Reseau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "reseau")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MetaGroupe> groupes = new HashSet<>();

    @OneToMany(mappedBy = "reseau")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TypeMetaGroupe> types = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Reseau nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<MetaGroupe> getGroupes() {
        return groupes;
    }

    public Reseau groupes(Set<MetaGroupe> metaGroupes) {
        this.groupes = metaGroupes;
        return this;
    }

    public Reseau addGroupes(MetaGroupe metaGroupe) {
        this.groupes.add(metaGroupe);
        metaGroupe.setReseau(this);
        return this;
    }

    public Reseau removeGroupes(MetaGroupe metaGroupe) {
        this.groupes.remove(metaGroupe);
        metaGroupe.setReseau(null);
        return this;
    }

    public void setGroupes(Set<MetaGroupe> metaGroupes) {
        this.groupes = metaGroupes;
    }

    public Set<TypeMetaGroupe> getTypes() {
        return types;
    }

    public Reseau types(Set<TypeMetaGroupe> typeMetaGroupes) {
        this.types = typeMetaGroupes;
        return this;
    }

    public Reseau addTypes(TypeMetaGroupe typeMetaGroupe) {
        this.types.add(typeMetaGroupe);
        typeMetaGroupe.setReseau(this);
        return this;
    }

    public Reseau removeTypes(TypeMetaGroupe typeMetaGroupe) {
        this.types.remove(typeMetaGroupe);
        typeMetaGroupe.setReseau(null);
        return this;
    }

    public void setTypes(Set<TypeMetaGroupe> typeMetaGroupes) {
        this.types = typeMetaGroupes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reseau)) {
            return false;
        }
        return id != null && id.equals(((Reseau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reseau{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
