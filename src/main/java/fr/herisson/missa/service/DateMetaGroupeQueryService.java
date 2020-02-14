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

import fr.herisson.missa.domain.DateMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.DateMetaGroupeRepository;
import fr.herisson.missa.repository.search.DateMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.DateMetaGroupeCriteria;
import fr.herisson.missa.service.dto.DateMetaGroupeDTO;
import fr.herisson.missa.service.mapper.DateMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link DateMetaGroupe} entities in the database.
 * The main input is a {@link DateMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DateMetaGroupeDTO} or a {@link Page} of {@link DateMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DateMetaGroupeQueryService extends QueryService<DateMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(DateMetaGroupeQueryService.class);

    private final DateMetaGroupeRepository dateMetaGroupeRepository;

    private final DateMetaGroupeMapper dateMetaGroupeMapper;

    private final DateMetaGroupeSearchRepository dateMetaGroupeSearchRepository;

    public DateMetaGroupeQueryService(DateMetaGroupeRepository dateMetaGroupeRepository, DateMetaGroupeMapper dateMetaGroupeMapper, DateMetaGroupeSearchRepository dateMetaGroupeSearchRepository) {
        this.dateMetaGroupeRepository = dateMetaGroupeRepository;
        this.dateMetaGroupeMapper = dateMetaGroupeMapper;
        this.dateMetaGroupeSearchRepository = dateMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DateMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DateMetaGroupeDTO> findByCriteria(DateMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DateMetaGroupe> specification = createSpecification(criteria);
        return dateMetaGroupeMapper.toDto(dateMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DateMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DateMetaGroupeDTO> findByCriteria(DateMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DateMetaGroupe> specification = createSpecification(criteria);
        return dateMetaGroupeRepository.findAll(specification, page)
            .map(dateMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DateMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DateMetaGroupe> specification = createSpecification(criteria);
        return dateMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link DateMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DateMetaGroupe> createSpecification(DateMetaGroupeCriteria criteria) {
        Specification<DateMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DateMetaGroupe_.id));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebut(), DateMetaGroupe_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), DateMetaGroupe_.dateFin));
            }
            if (criteria.getEvery() != null) {
                specification = specification.and(buildSpecification(criteria.getEvery(), DateMetaGroupe_.every));
            }
            if (criteria.getHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHour(), DateMetaGroupe_.hour));
            }
            if (criteria.getMinutes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinutes(), DateMetaGroupe_.minutes));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(DateMetaGroupe_.groupe, JoinType.LEFT).get(MetaGroupe_.id)));
            }
        }
        return specification;
    }
}
