package com.example.tourappapi.utils.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private ModelMapper mapper;

    public Mapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <O, I> O convertOneDim(I data, Class<O> entityClass) {
        return mapper.map(data, entityClass);
    }
}
