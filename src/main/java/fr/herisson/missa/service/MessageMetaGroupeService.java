package fr.herisson.missa.service;

import fr.herisson.missa.domain.MessageMetaGroupe;
import fr.herisson.missa.repository.MessageMetaGroupeRepository;
import fr.herisson.missa.repository.search.MessageMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MessageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MessageMetaGroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MessageMetaGroupe}.
 */
@Service
@Transactional
public class MessageMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(MessageMetaGroupeService.class);

    private final MessageMetaGroupeRepository messageMetaGroupeRepository;

    private final MessageMetaGroupeMapper messageMetaGroupeMapper;

    private final MessageMetaGroupeSearchRepository messageMetaGroupeSearchRepository;

    public MessageMetaGroupeService(MessageMetaGroupeRepository messageMetaGroupeRepository, MessageMetaGroupeMapper messageMetaGroupeMapper, MessageMetaGroupeSearchRepository messageMetaGroupeSearchRepository) {
        this.messageMetaGroupeRepository = messageMetaGroupeRepository;
        this.messageMetaGroupeMapper = messageMetaGroupeMapper;
        this.messageMetaGroupeSearchRepository = messageMetaGroupeSearchRepository;
    }

    /**
     * Save a messageMetaGroupe.
     *
     * @param messageMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public MessageMetaGroupeDTO save(MessageMetaGroupeDTO messageMetaGroupeDTO) {
        log.debug("Request to save MessageMetaGroupe : {}", messageMetaGroupeDTO);
        MessageMetaGroupe messageMetaGroupe = messageMetaGroupeMapper.toEntity(messageMetaGroupeDTO);
        messageMetaGroupe = messageMetaGroupeRepository.save(messageMetaGroupe);
        MessageMetaGroupeDTO result = messageMetaGroupeMapper.toDto(messageMetaGroupe);
        messageMetaGroupeSearchRepository.save(messageMetaGroupe);
        return result;
    }

    /**
     * Get all the messageMetaGroupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageMetaGroupeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageMetaGroupes");
        return messageMetaGroupeRepository.findAll(pageable)
            .map(messageMetaGroupeMapper::toDto);
    }

    /**
     * Get one messageMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessageMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get MessageMetaGroupe : {}", id);
        return messageMetaGroupeRepository.findById(id)
            .map(messageMetaGroupeMapper::toDto);
    }

    /**
     * Delete the messageMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageMetaGroupe : {}", id);
        messageMetaGroupeRepository.deleteById(id);
        messageMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the messageMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageMetaGroupeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MessageMetaGroupes for query {}", query);
        return messageMetaGroupeSearchRepository.search(queryStringQuery(query), pageable)
            .map(messageMetaGroupeMapper::toDto);
    }
}
