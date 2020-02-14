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

import fr.herisson.missa.domain.MembreMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.MembreMetaGroupeRepository;
import fr.herisson.missa.repository.search.MembreMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MembreMetaGroupeCriteria;
import fr.herisson.missa.service.dto.MembreMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MembreMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link MembreMetaGroupe} entities in the database.
 * The main input is a {@link MembreMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MembreMetaGroupeDTO} or a {@link Page} of {@link MembreMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MembreMetaGroupeQueryService extends QueryService<MembreMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(MembreMetaGroupeQueryService.class);

    private final MembreMetaGroupeRepository membreMetaGroupeRepository;

    private final MembreMetaGroupeMapper membreMetaGroupeMapper;

    private final MembreMetaGroupeSearchRepository membreMetaGroupeSearchRepository;

    public MembreMetaGroupeQueryService(MembreMetaGroupeRepository membreMetaGroupeRepository, MembreMetaGroupeMapper membreMetaGroupeMapper, MembreMetaGroupeSearchRepository membreMetaGroupeSearchRepository) {
        this.membreMetaGroupeRepository = membreMetaGroupeRepository;
        this.membreMetaGroupeMapper = membreMetaGroupeMapper;
        this.membreMetaGroupeSearchRepository = membreMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MembreMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MembreMetaGroupeDTO> findByCriteria(MembreMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MembreMetaGroupe> specification = createSpecification(criteria);
        return membreMetaGroupeMapper.toDto(membreMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MembreMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MembreMetaGroupeDTO> findByCriteria(MembreMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MembreMetaGroupe> specification = createSpecification(criteria);
        return membreMetaGroupeRepository.findAll(specification, page)
            .map(membreMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MembreMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MembreMetaGroupe> specification = createSpecification(criteria);
        return membreMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link MembreMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MembreMetaGroupe> createSpecification(MembreMetaGroupeCriteria criteria) {
        Specification<MembreMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MembreMetaGroupe_.id));
            }
            if (criteria.getValidated() != null) {
                specification = specification.and(buildSpecification(criteria.getValidated(), MembreMetaGroupe_.validated));
            }
            if (criteria.getDateValidation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateValidation(), MembreMetaGroupe_.dateValidation));
            }
            if (criteria.getAdmin() != null) {
                specification = specification.and(buildSpecification(criteria.getAdmin(), MembreMetaGroupe_.admin));
            }
            if (criteria.getMailingList() != null) {
                specification = specification.and(buildSpecification(criteria.getMailingList(), MembreMetaGroupe_.mailingList));
            }
            if (criteria.getGroupeMembreId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeMembreId(),
                    root -> root.join(MembreMetaGroupe_.groupeMembre, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getValideParId() != null) {
                specification = specification.and(buildSpecification(criteria.getValideParId(),
                    root -> root.join(MembreMetaGroupe_.validePar, JoinType.LEFT).get(MissaUser_.id)));
            }
            if (criteria.getMissaUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getMissaUserId(),
                    root -> root.join(MembreMetaGroupe_.missaUser, JoinType.LEFT).get(MissaUser_.id)));
            }
        }
        return specification;
    }
}
