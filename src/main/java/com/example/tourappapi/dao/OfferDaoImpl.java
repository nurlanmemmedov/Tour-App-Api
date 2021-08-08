package com.example.tourappapi.dao;

import com.example.tourappapi.dao.interfaces.OfferDao;
import com.example.tourappapi.models.Offer;
import com.example.tourappapi.repositories.OfferRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferDaoImpl implements OfferDao {

    private OfferRepository repository;

    public OfferDaoImpl(OfferRepository repository){
        this.repository = repository;
    }

    @Override
    public Offer save(Offer offer) {
        return repository.save(offer);
    }

    @Override
    public void delete(String username, Integer id) {
    }

    @Override
    public Offer getById(Integer id) {
        return repository.getById(id);
    }

    @Override
    public Offer getByIdAndUsername(String username, Integer id) {
        return repository.get(username, id);
    }

    @Override
    public List<Offer> getAll(String username) {
        return repository.findAll(username);
    }
}
