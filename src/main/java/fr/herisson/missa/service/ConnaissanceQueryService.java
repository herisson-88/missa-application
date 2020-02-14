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

import fr.herisson.missa.domain.Connaissance;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.ConnaissanceRepository;
import fr.herisson.missa.repository.search.ConnaissanceSearchRepository;
import fr.herisson.missa.service.dto.ConnaissanceCriteria;
import fr.herisson.missa.service.dto.ConnaissanceDTO;
import fr.herisson.missa.service.mapper.ConnaissanceMapper;

/**
 * Service for executing complex queries for {@link Connaissance} entities in the database.
 * The main input is a {@link ConnaissanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConnaissanceDTO} or a {@link Page} of {@link ConnaissanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConnaissanceQueryService extends QueryService<Connaissance> {

    private final Logger log = LoggerFactory.getLogger(ConnaissanceQueryService.class);

    private final ConnaissanceRepository connaissanceRepository;

    private final ConnaissanceMapper connaissanceMapper;

    private final ConnaissanceSearchRepository connaissanceSearchRepository;

    public ConnaissanceQueryService(ConnaissanceRepository connaissanceRepository, ConnaissanceMapper connaissanceMapper, ConnaissanceSearchRepository connaissanceSearchRepository) {
        this.connaissanceRepository = connaissanceRepository;
        this.connaissanceMapper = connaissanceMapper;
        this.connaissanceSearchRepository = connaissanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ConnaissanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConnaissanceDTO> findByCriteria(ConnaissanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Connaissance> specification = createSpecification(criteria);
        return connaissanceMapper.toDto(connaissanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConnaissanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnaissanceDTO> findByCriteria(ConnaissanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Connaissance> specification = createSpecification(criteria);
        return connaissanceRepository.findAll(specification, page)
            .map(connaissanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConnaissanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Connaissance> specification = createSpecification(criteria);
        return connaissanceRepository.count(specification);
    }

    /**
     * Function to convert {@link ConnaissanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Connaissance> createSpecification(ConnaissanceCriteria criteria) {
        Specification<Connaissance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Connaissance_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Connaissance_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Connaissance_.prenom));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), Connaissance_.mail));
            }
            if (criteria.getIdFaceBook() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdFaceBook(), Connaissance_.idFaceBook));
            }
            if (criteria.getParraine() != null) {
                specification = specification.and(buildSpecification(criteria.getParraine(), Connaissance_.parraine));
            }
            if (criteria.getConnuParId() != null) {
                specification = specification.and(buildSpecification(criteria.getConnuParId(),
                    root -> root.join(Connaissance_.connuPar, JoinType.LEFT).get(MissaUser_.id)));
            }
        }
        return specification;
    }
}
