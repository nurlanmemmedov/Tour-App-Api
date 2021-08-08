package com.example.tourappapi.services.interfaces;

import com.example.tourappapi.dto.AgentRequestDto;
import com.example.tourappapi.dto.AgentRequestListDto;
import com.example.tourappapi.models.AgentRequest;
import com.example.tourappapi.models.Request;
import com.example.tourappapi.utils.pagination.Paging;

import java.util.List;

/**
 * represents the AgentRequest service throughout the application
 * is used to make operations with AgentRequests
 */
public interface AgentRequestService {

    /**
     * saves agent request
     * @param agentRequest
     * @return
     */
    AgentRequest save(AgentRequest agentRequest);

    /**
     * Create new agent requests by request
     * @param request
     */
    void createByRequest(Request request);

    /**
     * toggles isArchived of agent request
     * @param id
     * @param username
     * @return
     */
    boolean toggleArchived(Integer id, String username);

    /**
     * gets agent request by id
     * @param id
     * @return
     */
    AgentRequest getById(Integer id);

    /**
     * gets agent request by id and username
     * @param id
     * @param username
     * @return
     */
    AgentRequestDto getByIdAndUsername(Integer id, String username);

    /**
     * finds agent requests by status paginated
     * @param status
     * @param username
     * @param index
     * @param size
     * @return
     */
    Paging<AgentRequestListDto> findByStatus(String status, String username, Integer index, Integer size);

    /**
     * gets archived requests of agent paginated
     * @param username
     * @param index
     * @param size
     * @return
     */
    Paging<AgentRequestListDto> getArchivedRequests(String username, Integer index, Integer size);

    /**
     * gets all requests of agent paginated
     * @param username
     * @param index
     * @param size
     * @return
     */
    Paging<AgentRequestListDto> getAll(String username, Integer index, Integer size);

    /**
     * gets list of agent requests by request id
     * @param id
     * @return
     */
    List<AgentRequest> getAllByRequestId(Integer id);
}
