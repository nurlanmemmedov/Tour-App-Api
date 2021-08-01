package com.example.tourappapi.services;

import com.example.tourappapi.dao.interfaces.AgentDao;
import com.example.tourappapi.exceptions.EmailAlreadyExistsException;
import com.example.tourappapi.exceptions.UserNotFoundException;
import com.example.tourappapi.exceptions.UsernameAlreadyExistsException;
import com.example.tourappapi.exceptions.VoenAlreadyExistsException;
import com.example.tourappapi.models.Agent;
import com.example.tourappapi.services.interfaces.AgentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    private AgentDao dao;

    public AgentServiceImpl(AgentDao dao){
        this.dao = dao;
    }

    @Override
    public Agent save(Agent agent) {
        if (agent.getId() != null) return dao.create(agent);
        checkIfExists(agent);
        return dao.create(agent);
    }

    @Override
    public Agent getById(Integer id) {
        Agent agent = dao.getById(id);
        if (agent == null) throw new UserNotFoundException();
        return agent;
    }

    @Override
    public Agent getByUsername(String username) {
        System.out.println(username);
        Agent agent = dao.getByUsername(username);
        if (agent == null) throw new UserNotFoundException();
        return agent;
    }

    @Override
    public Agent getByEmail(String email) {
        Agent agent = dao.getByEmail(email);
        if (agent == null) throw new UserNotFoundException();
        return agent;
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public List<Agent> getAll() {
        return dao.getAll();
    }

    @Override
    public Boolean checkIfExists(Agent agent) {
        if (dao.getByUsername(agent.getUsername())!=null){
            throw new UsernameAlreadyExistsException();
        }else if(dao.getByEmail(agent.getEmail())!=null){
            throw new EmailAlreadyExistsException();
        }else if(dao.getByVoen(agent.getVoen())!=null){
            throw new VoenAlreadyExistsException();
        }
        return false;
    }
}
