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

import fr.herisson.missa.domain.PartageMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.PartageMetaGroupeRepository;
import fr.herisson.missa.repository.search.PartageMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.PartageMetaGroupeCriteria;
import fr.herisson.missa.service.dto.PartageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.PartageMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link PartageMetaGroupe} entities in the database.
 * The main input is a {@link PartageMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartageMetaGroupeDTO} or a {@link Page} of {@link PartageMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartageMetaGroupeQueryService extends QueryService<PartageMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(PartageMetaGroupeQueryService.class);

    private final PartageMetaGroupeRepository partageMetaGroupeRepository;

    private final PartageMetaGroupeMapper partageMetaGroupeMapper;

    private final PartageMetaGroupeSearchRepository partageMetaGroupeSearchRepository;

    public PartageMetaGroupeQueryService(PartageMetaGroupeRepository partageMetaGroupeRepository, PartageMetaGroupeMapper partageMetaGroupeMapper, PartageMetaGroupeSearchRepository partageMetaGroupeSearchRepository) {
        this.partageMetaGroupeRepository = partageMetaGroupeRepository;
        this.partageMetaGroupeMapper = partageMetaGroupeMapper;
        this.partageMetaGroupeSearchRepository = partageMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PartageMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartageMetaGroupeDTO> findByCriteria(PartageMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartageMetaGroupe> specification = createSpecification(criteria);
        return partageMetaGroupeMapper.toDto(partageMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PartageMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartageMetaGroupeDTO> findByCriteria(PartageMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartageMetaGroupe> specification = createSpecification(criteria);
        return partageMetaGroupeRepository.findAll(specification, page)
            .map(partageMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartageMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartageMetaGroupe> specification = createSpecification(criteria);
        return partageMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link PartageMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartageMetaGroupe> createSpecification(PartageMetaGroupeCriteria criteria) {
        Specification<PartageMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartageMetaGroupe_.id));
            }
            if (criteria.getValidated() != null) {
                specification = specification.and(buildSpecification(criteria.getValidated(), PartageMetaGroupe_.validated));
            }
            if (criteria.getDateValidation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateValidation(), PartageMetaGroupe_.dateValidation));
            }
            if (criteria.getMetaGroupePartageId() != null) {
                specification = specification.and(buildSpecification(criteria.getMetaGroupePartageId(),
                    root -> root.join(PartageMetaGroupe_.metaGroupePartage, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getMetaGroupeDestinatairesId() != null) {
                specification = specification.and(buildSpecification(criteria.getMetaGroupeDestinatairesId(),
                    root -> root.join(PartageMetaGroupe_.metaGroupeDestinataires, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getAuteurPartageId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuteurPartageId(),
                    root -> root.join(PartageMetaGroupe_.auteurPartage, JoinType.LEFT).get(MissaUser_.id)));
            }
            if (criteria.getValidateurDuPartageId() != null) {
                specification = specification.and(buildSpecification(criteria.getValidateurDuPartageId(),
                    root -> root.join(PartageMetaGroupe_.validateurDuPartage, JoinType.LEFT).get(MissaUser_.id)));
            }
        }
        return specification;
    }
}
