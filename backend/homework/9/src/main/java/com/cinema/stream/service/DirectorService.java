package com.cinema.stream.service;

import com.cinema.stream.entity.Director;
import com.cinema.stream.repository.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository){

        this.directorRepository = directorRepository;
    }

    public Director getDirector(Long id){
        return directorRepository.getReferenceById(id);
    }
}
