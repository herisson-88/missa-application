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

import fr.herisson.missa.domain.Reseau;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.ReseauRepository;
import fr.herisson.missa.repository.search.ReseauSearchRepository;
import fr.herisson.missa.service.dto.ReseauCriteria;
import fr.herisson.missa.service.dto.ReseauDTO;
import fr.herisson.missa.service.mapper.ReseauMapper;

/**
 * Service for executing complex queries for {@link Reseau} entities in the database.
 * The main input is a {@link ReseauCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReseauDTO} or a {@link Page} of {@link ReseauDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReseauQueryService extends QueryService<Reseau> {

    private final Logger log = LoggerFactory.getLogger(ReseauQueryService.class);

    private final ReseauRepository reseauRepository;

    private final ReseauMapper reseauMapper;

    private final ReseauSearchRepository reseauSearchRepository;

    public ReseauQueryService(ReseauRepository reseauRepository, ReseauMapper reseauMapper, ReseauSearchRepository reseauSearchRepository) {
        this.reseauRepository = reseauRepository;
        this.reseauMapper = reseauMapper;
        this.reseauSearchRepository = reseauSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ReseauDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReseauDTO> findByCriteria(ReseauCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reseau> specification = createSpecification(criteria);
        return reseauMapper.toDto(reseauRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReseauDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReseauDTO> findByCriteria(ReseauCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reseau> specification = createSpecification(criteria);
        return reseauRepository.findAll(specification, page)
            .map(reseauMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReseauCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reseau> specification = createSpecification(criteria);
        return reseauRepository.count(specification);
    }

    /**
     * Function to convert {@link ReseauCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reseau> createSpecification(ReseauCriteria criteria) {
        Specification<Reseau> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reseau_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Reseau_.nom));
            }
            if (criteria.getGroupesId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupesId(),
                    root -> root.join(Reseau_.groupes, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getTypesId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypesId(),
                    root -> root.join(Reseau_.types, JoinType.LEFT).get(TypeMetaGroupe_.id)));
            }
        }
        return specification;
    }
}
