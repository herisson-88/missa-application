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

import fr.herisson.missa.domain.MessageMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.MessageMetaGroupeRepository;
import fr.herisson.missa.repository.search.MessageMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MessageMetaGroupeCriteria;
import fr.herisson.missa.service.dto.MessageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MessageMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link MessageMetaGroupe} entities in the database.
 * The main input is a {@link MessageMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageMetaGroupeDTO} or a {@link Page} of {@link MessageMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageMetaGroupeQueryService extends QueryService<MessageMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(MessageMetaGroupeQueryService.class);

    private final MessageMetaGroupeRepository messageMetaGroupeRepository;

    private final MessageMetaGroupeMapper messageMetaGroupeMapper;

    private final MessageMetaGroupeSearchRepository messageMetaGroupeSearchRepository;

    public MessageMetaGroupeQueryService(MessageMetaGroupeRepository messageMetaGroupeRepository, MessageMetaGroupeMapper messageMetaGroupeMapper, MessageMetaGroupeSearchRepository messageMetaGroupeSearchRepository) {
        this.messageMetaGroupeRepository = messageMetaGroupeRepository;
        this.messageMetaGroupeMapper = messageMetaGroupeMapper;
        this.messageMetaGroupeSearchRepository = messageMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MessageMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageMetaGroupeDTO> findByCriteria(MessageMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MessageMetaGroupe> specification = createSpecification(criteria);
        return messageMetaGroupeMapper.toDto(messageMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageMetaGroupeDTO> findByCriteria(MessageMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MessageMetaGroupe> specification = createSpecification(criteria);
        return messageMetaGroupeRepository.findAll(specification, page)
            .map(messageMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MessageMetaGroupe> specification = createSpecification(criteria);
        return messageMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MessageMetaGroupe> createSpecification(MessageMetaGroupeCriteria criteria) {
        Specification<MessageMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MessageMetaGroupe_.id));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), MessageMetaGroupe_.titre));
            }
            if (criteria.getPublique() != null) {
                specification = specification.and(buildSpecification(criteria.getPublique(), MessageMetaGroupe_.publique));
            }
            if (criteria.getAuteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuteurId(),
                    root -> root.join(MessageMetaGroupe_.auteur, JoinType.LEFT).get(MissaUser_.id)));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(MessageMetaGroupe_.groupe, JoinType.LEFT).get(MetaGroupe_.id)));
            }
        }
        return specification;
    }
}
