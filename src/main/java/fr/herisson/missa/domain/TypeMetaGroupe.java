package fr.herisson.missa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeMetaGroupe.
 */
@Entity
@Table(name = "type_meta_groupe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "typemetagroupe")
public class TypeMetaGroupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "type_du_groupe", length = 100, nullable = false)
    private String typeDuGroupe;

    @Size(max = 200)
    @Column(name = "icone_fa", length = 200)
    private String iconeFa;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @Column(name = "ordre_mail")
    private Integer ordreMail;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LienDocMetaGroupe> defaultDocs = new HashSet<>();

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MetaGroupe> groupes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("types")
    private Reseau reseau;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeDuGroupe() {
        return typeDuGroupe;
    }

    public TypeMetaGroupe typeDuGroupe(String typeDuGroupe) {
        this.typeDuGroupe = typeDuGroupe;
        return this;
    }

    public void setTypeDuGroupe(String typeDuGroupe) {
        this.typeDuGroupe = typeDuGroupe;
    }

    public String getIconeFa() {
        return iconeFa;
    }

    public TypeMetaGroupe iconeFa(String iconeFa) {
        this.iconeFa = iconeFa;
        return this;
    }

    public void setIconeFa(String iconeFa) {
        this.iconeFa = iconeFa;
    }

    public String getDetail() {
        return detail;
    }

    public TypeMetaGroupe detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getOrdreMail() {
        return ordreMail;
    }

    public TypeMetaGroupe ordreMail(Integer ordreMail) {
        this.ordreMail = ordreMail;
        return this;
    }

    public void setOrdreMail(Integer ordreMail) {
        this.ordreMail = ordreMail;
    }

    public Set<LienDocMetaGroupe> getDefaultDocs() {
        return defaultDocs;
    }

    public TypeMetaGroupe defaultDocs(Set<LienDocMetaGroupe> lienDocMetaGroupes) {
        this.defaultDocs = lienDocMetaGroupes;
        return this;
    }

    public TypeMetaGroupe addDefaultDocs(LienDocMetaGroupe lienDocMetaGroupe) {
        this.defaultDocs.add(lienDocMetaGroupe);
        lienDocMetaGroupe.setType(this);
        return this;
    }

    public TypeMetaGroupe removeDefaultDocs(LienDocMetaGroupe lienDocMetaGroupe) {
        this.defaultDocs.remove(lienDocMetaGroupe);
        lienDocMetaGroupe.setType(null);
        return this;
    }

    public void setDefaultDocs(Set<LienDocMetaGroupe> lienDocMetaGroupes) {
        this.defaultDocs = lienDocMetaGroupes;
    }

    public Set<MetaGroupe> getGroupes() {
        return groupes;
    }

    public TypeMetaGroupe groupes(Set<MetaGroupe> metaGroupes) {
        this.groupes = metaGroupes;
        return this;
    }

    public TypeMetaGroupe addGroupes(MetaGroupe metaGroupe) {
        this.groupes.add(metaGroupe);
        metaGroupe.setType(this);
        return this;
    }

    public TypeMetaGroupe removeGroupes(MetaGroupe metaGroupe) {
        this.groupes.remove(metaGroupe);
        metaGroupe.setType(null);
        return this;
    }

    public void setGroupes(Set<MetaGroupe> metaGroupes) {
        this.groupes = metaGroupes;
    }

    public Reseau getReseau() {
        return reseau;
    }

    public TypeMetaGroupe reseau(Reseau reseau) {
        this.reseau = reseau;
        return this;
    }

    public void setReseau(Reseau reseau) {
        this.reseau = reseau;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeMetaGroupe)) {
            return false;
        }
        return id != null && id.equals(((TypeMetaGroupe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeMetaGroupe{" +
            "id=" + getId() +
            ", typeDuGroupe='" + getTypeDuGroupe() + "'" +
            ", iconeFa='" + getIconeFa() + "'" +
            ", detail='" + getDetail() + "'" +
            ", ordreMail=" + getOrdreMail() +
            "}";
    }
}
