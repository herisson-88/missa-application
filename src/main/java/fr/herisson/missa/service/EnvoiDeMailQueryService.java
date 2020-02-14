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

import fr.herisson.missa.domain.EnvoiDeMail;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.EnvoiDeMailRepository;
import fr.herisson.missa.repository.search.EnvoiDeMailSearchRepository;
import fr.herisson.missa.service.dto.EnvoiDeMailCriteria;
import fr.herisson.missa.service.dto.EnvoiDeMailDTO;
import fr.herisson.missa.service.mapper.EnvoiDeMailMapper;

/**
 * Service for executing complex queries for {@link EnvoiDeMail} entities in the database.
 * The main input is a {@link EnvoiDeMailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnvoiDeMailDTO} or a {@link Page} of {@link EnvoiDeMailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnvoiDeMailQueryService extends QueryService<EnvoiDeMail> {

    private final Logger log = LoggerFactory.getLogger(EnvoiDeMailQueryService.class);

    private final EnvoiDeMailRepository envoiDeMailRepository;

    private final EnvoiDeMailMapper envoiDeMailMapper;

    private final EnvoiDeMailSearchRepository envoiDeMailSearchRepository;

    public EnvoiDeMailQueryService(EnvoiDeMailRepository envoiDeMailRepository, EnvoiDeMailMapper envoiDeMailMapper, EnvoiDeMailSearchRepository envoiDeMailSearchRepository) {
        this.envoiDeMailRepository = envoiDeMailRepository;
        this.envoiDeMailMapper = envoiDeMailMapper;
        this.envoiDeMailSearchRepository = envoiDeMailSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EnvoiDeMailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnvoiDeMailDTO> findByCriteria(EnvoiDeMailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnvoiDeMail> specification = createSpecification(criteria);
        return envoiDeMailMapper.toDto(envoiDeMailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnvoiDeMailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvoiDeMailDTO> findByCriteria(EnvoiDeMailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnvoiDeMail> specification = createSpecification(criteria);
        return envoiDeMailRepository.findAll(specification, page)
            .map(envoiDeMailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnvoiDeMailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnvoiDeMail> specification = createSpecification(criteria);
        return envoiDeMailRepository.count(specification);
    }

    /**
     * Function to convert {@link EnvoiDeMailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnvoiDeMail> createSpecification(EnvoiDeMailCriteria criteria) {
        Specification<EnvoiDeMail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnvoiDeMail_.id));
            }
            if (criteria.getDateEnvoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEnvoi(), EnvoiDeMail_.dateEnvoi));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), EnvoiDeMail_.titre));
            }
            if (criteria.getNbDestinataire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbDestinataire(), EnvoiDeMail_.nbDestinataire));
            }
            if (criteria.getSended() != null) {
                specification = specification.and(buildSpecification(criteria.getSended(), EnvoiDeMail_.sended));
            }
            if (criteria.getGroupePartageParMailId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupePartageParMailId(),
                    root -> root.join(EnvoiDeMail_.groupePartageParMails, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getAdminId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdminId(),
                    root -> root.join(EnvoiDeMail_.admin, JoinType.LEFT).get(MissaUser_.id)));
            }
            if (criteria.getGroupeDuMailId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeDuMailId(),
                    root -> root.join(EnvoiDeMail_.groupeDuMail, JoinType.LEFT).get(MetaGroupe_.id)));
            }
        }
        return specification;
    }
}
