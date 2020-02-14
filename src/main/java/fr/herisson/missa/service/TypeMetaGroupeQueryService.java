package fr.herisson.missa.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.TypeMetaGroupeRepository;
import fr.herisson.missa.repository.search.TypeMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.TypeMetaGroupeCriteria;
import fr.herisson.missa.service.dto.TypeMetaGroupeDTO;
import fr.herisson.missa.service.mapper.TypeMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link TypeMetaGroupe} entities in the database.
 * The main input is a {@link TypeMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeMetaGroupeDTO} or a {@link Page} of {@link TypeMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeMetaGroupeQueryService extends QueryService<TypeMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(TypeMetaGroupeQueryService.class);

    private final TypeMetaGroupeRepository typeMetaGroupeRepository;

    private final TypeMetaGroupeMapper typeMetaGroupeMapper;

    private final TypeMetaGroupeSearchRepository typeMetaGroupeSearchRepository;

    public TypeMetaGroupeQueryService(TypeMetaGroupeRepository typeMetaGroupeRepository, TypeMetaGroupeMapper typeMetaGroupeMapper, TypeMetaGroupeSearchRepository typeMetaGroupeSearchRepository) {
        this.typeMetaGroupeRepository = typeMetaGroupeRepository;
        this.typeMetaGroupeMapper = typeMetaGroupeMapper;
        this.typeMetaGroupeSearchRepository = typeMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TypeMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeMetaGroupeDTO> findByCriteria(TypeMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeMetaGroupe> specification = createSpecification(criteria);
        return typeMetaGroupeMapper.toDto(typeMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeMetaGroupeDTO> findByCriteria(TypeMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeMetaGroupe> specification = createSpecification(criteria);
        return typeMetaGroupeRepository.findAll(specification, page)
            .map(typeMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeMetaGroupe> specification = createSpecification(criteria);
        return typeMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link TypeMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypeMetaGroupe> createSpecification(TypeMetaGroupeCriteria criteria) {
        Specification<TypeMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypeMetaGroupe_.id));
            }
            if (criteria.getTypeDuGroupe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeDuGroupe(), TypeMetaGroupe_.typeDuGroupe));
            }
            if (criteria.getIconeFa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconeFa(), TypeMetaGroupe_.iconeFa));
            }
            if (criteria.getOrdreMail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdreMail(), TypeMetaGroupe_.ordreMail));
            }
            if (criteria.getDefaultDocsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultDocsId(),
                    root -> root.join(TypeMetaGroupe_.defaultDocs, JoinType.LEFT).get(LienDocMetaGroupe_.id)));
            }
            if (criteria.getGroupesId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupesId(),
                    root -> root.join(TypeMetaGroupe_.groupes, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getReseauId() != null) {
                specification = specification.and(buildSpecification(criteria.getReseauId(),
                    root -> root.join(TypeMetaGroupe_.reseau, JoinType.LEFT).get(Reseau_.id)));
            }
        }
        return specification;
    }
}
