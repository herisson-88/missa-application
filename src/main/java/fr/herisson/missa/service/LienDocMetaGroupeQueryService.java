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

import fr.herisson.missa.domain.LienDocMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.LienDocMetaGroupeRepository;
import fr.herisson.missa.repository.search.LienDocMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.LienDocMetaGroupeCriteria;
import fr.herisson.missa.service.dto.LienDocMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LienDocMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link LienDocMetaGroupe} entities in the database.
 * The main input is a {@link LienDocMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LienDocMetaGroupeDTO} or a {@link Page} of {@link LienDocMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LienDocMetaGroupeQueryService extends QueryService<LienDocMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(LienDocMetaGroupeQueryService.class);

    private final LienDocMetaGroupeRepository lienDocMetaGroupeRepository;

    private final LienDocMetaGroupeMapper lienDocMetaGroupeMapper;

    private final LienDocMetaGroupeSearchRepository lienDocMetaGroupeSearchRepository;

    public LienDocMetaGroupeQueryService(LienDocMetaGroupeRepository lienDocMetaGroupeRepository, LienDocMetaGroupeMapper lienDocMetaGroupeMapper, LienDocMetaGroupeSearchRepository lienDocMetaGroupeSearchRepository) {
        this.lienDocMetaGroupeRepository = lienDocMetaGroupeRepository;
        this.lienDocMetaGroupeMapper = lienDocMetaGroupeMapper;
        this.lienDocMetaGroupeSearchRepository = lienDocMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LienDocMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LienDocMetaGroupeDTO> findByCriteria(LienDocMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LienDocMetaGroupe> specification = createSpecification(criteria);
        return lienDocMetaGroupeMapper.toDto(lienDocMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LienDocMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LienDocMetaGroupeDTO> findByCriteria(LienDocMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LienDocMetaGroupe> specification = createSpecification(criteria);
        return lienDocMetaGroupeRepository.findAll(specification, page)
            .map(lienDocMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LienDocMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LienDocMetaGroupe> specification = createSpecification(criteria);
        return lienDocMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link LienDocMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LienDocMetaGroupe> createSpecification(LienDocMetaGroupeCriteria criteria) {
        Specification<LienDocMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LienDocMetaGroupe_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), LienDocMetaGroupe_.nom));
            }
            if (criteria.getTypeDuDoc() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeDuDoc(), LienDocMetaGroupe_.typeDuDoc));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(LienDocMetaGroupe_.groupe, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(LienDocMetaGroupe_.type, JoinType.LEFT).get(TypeMetaGroupe_.id)));
            }
        }
        return specification;
    }
}
