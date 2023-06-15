package com.study.commons.configs;

import com.study.entities.Configs;
import com.study.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {

    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs configs = repository.findById(code).orElse(null);
        if (configs == null) {
            return;
        }

        repository.delete(configs);
        repository.flush();
    }

}
