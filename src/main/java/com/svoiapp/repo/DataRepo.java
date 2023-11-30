package com.svoiapp.repo;

import com.svoiapp.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepo extends JpaRepository<DataEntity, Long> {

    DataEntity findDataEntityByLogin(String login);

    Boolean existsByLogin (String login);

    Boolean existsByEmail (String email);

    DataEntity findDataEntityByLoginAndEmail (String login, String email);


}
