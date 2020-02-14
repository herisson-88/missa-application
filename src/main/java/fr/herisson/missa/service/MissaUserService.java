package fr.herisson.missa.service;

import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.repository.MissaUserRepository;
import fr.herisson.missa.repository.search.MissaUserSearchRepository;
import fr.herisson.missa.service.dto.MissaUserDTO;
import fr.herisson.missa.service.mapper.MissaUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MissaUser}.
 */
@Service
@Transactional
public class MissaUserService {

    private final Logger log = LoggerFactory.getLogger(MissaUserService.class);

    private final MissaUserRepository missaUserRepository;

    private final MissaUserMapper missaUserMapper;

    private final MissaUserSearchRepository missaUserSearchRepository;

    public MissaUserService(MissaUserRepository missaUserRepository, MissaUserMapper missaUserMapper, MissaUserSearchRepository missaUserSearchRepository) {
        this.missaUserRepository = missaUserRepository;
        this.missaUserMapper = missaUserMapper;
        this.missaUserSearchRepository = missaUserSearchRepository;
    }

    /**
     * Save a missaUser.
     *
     * @param missaUserDTO the entity to save.
     * @return the persisted entity.
     */
    public MissaUserDTO save(MissaUserDTO missaUserDTO) {
        log.debug("Request to save MissaUser : {}", missaUserDTO);
        MissaUser missaUser = missaUserMapper.toEntity(missaUserDTO);
        missaUser = missaUserRepository.save(missaUser);
        MissaUserDTO result = missaUserMapper.toDto(missaUser);
        missaUserSearchRepository.save(missaUser);
        return result;
    }

    /**
     * Get all the missaUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MissaUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MissaUsers");
        return missaUserRepository.findAll(pageable)
            .map(missaUserMapper::toDto);
    }

    /**
     * Get one missaUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MissaUserDTO> findOne(Long id) {
        log.debug("Request to get MissaUser : {}", id);
        return missaUserRepository.findById(id)
            .map(missaUserMapper::toDto);
    }

    /**
     * Delete the missaUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MissaUser : {}", id);
        missaUserRepository.deleteById(id);
        missaUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the missaUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MissaUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MissaUsers for query {}", query);
        return missaUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(missaUserMapper::toDto);
    }
}
