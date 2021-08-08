package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.AgentRequestDao;
import com.example.tourappapi.enums.AgentRequestStatus;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.repositories.AgentRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.tourappapi.utils.pagination.PagingUtil.preparePage;

@Component
public class AgentRequestDaoImpl implements AgentRequestDao {

    private AgentRequestRepository repository;

    public AgentRequestDaoImpl(AgentRequestRepository repository){
        this.repository = repository;
    }

    @Override
    public AgentRequest save(AgentRequest request) {
        return repository.save(request);
    }

    @Override
    public void delete(String username, Integer id) {
        AgentRequest agentRequest = getByIdAndUsername(id, username);
         repository.deleteById(id);
    }

    @Override
    public AgentRequest getByIdAndUsername(Integer id, String username) {
        return repository.getById(id, username);
    }

    @Override
    public Page<AgentRequest> getArchivedRequests(String username, Integer index, Integer size) {
        Pageable paging = preparePage(index, size);
        return repository.getArchivedRequests(username, paging);
    }

    @Override
    public AgentRequest getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public Page<AgentRequest> getAllByStatus(AgentRequestStatus status, String username, Integer index, Integer size) {
        Pageable paging = preparePage(index, size);
        return repository.findAllByStatus(status, username, paging);
    }

    @Override
    public Page<AgentRequest> getAll(String username, Integer index, Integer size) {
        Pageable paging = preparePage(index, size);
        return repository.findAll(username, paging);
    }

    @Override
    public List<AgentRequest> getAllByRequestId(Integer id) {
        return repository.getAllByRequest(id);
    }

    @Override
    public void expireAgentRequests(Integer requestId) {
        repository.expireAgentRequests(requestId);
    }
}
