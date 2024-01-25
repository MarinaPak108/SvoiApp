package com.svoiapp.service;

import com.svoiapp.entity.AuthEntity;
import com.svoiapp.entity.DataEntity;
import com.svoiapp.formdata.CreateLoginFromData;
import com.svoiapp.formdata.CreateUserFormData;
import com.svoiapp.repo.AuthRepo;
import com.svoiapp.repo.DataRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final AuthRepo authRepo;
    private final DataRepo repo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder encoder;


    public UserService(AuthRepo authRepo, DataRepo repo) {
        this.authRepo = authRepo;
        this.repo = repo;
    }

    public DataEntity getData (String login){
        return repo.findDataEntityByLogin(login);
    }


    public List<Boolean> isUserExists (CreateLoginFromData loginForm){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<Boolean> resultList = new ArrayList<>();
        DataEntity result = repo.findDataEntityByLogin(loginForm.getUsername());
        if(result == null){
            resultList.addAll(Arrays.asList(false, false));
            return resultList;
        }
        Boolean loginRes = result.getLogin().isEmpty();
        logger.info(String.format("User with login %s exists: %s", loginForm.getUsername(), loginRes));
        resultList.add(!loginRes);
        Boolean pwdRes = encoder.matches(loginForm.getPassword(), result.getPwd());
        logger.info(String.format("User`s login %s password is %s", loginForm.getUsername(), pwdRes));
        resultList.add(pwdRes);
        return resultList;
    }

    public List<Boolean> isLoginEmailExists (String login, String email){
        List<Boolean> resultList = new ArrayList<>();
        Boolean loginRepo = repo.existsByLogin(login);
        logger.info(String.format("Login %s is avaliable: %s", login, !loginRepo));
        resultList.add(loginRepo);
        Boolean loginEmail = repo.existsByEmail(email);
        logger.info(String.format("Is email %s avaliable : %s", email, !loginEmail));
        resultList.add(loginEmail);
        return resultList;
    }

    public Boolean isEmailAuthorised (String login, String email){
        DataEntity data = repo.findDataEntityByLoginAndEmail(login, email);
        return data.getConfirmed();
    }


    public String createUser(CreateUserFormData formData) {
        try{
            Random rnd = new Random();
            int nmbr = rnd.nextInt(999999);
            String pin =  String.format("%06d", nmbr);
            DataEntity data = modelMapper.map(formData, DataEntity.class);
            AuthEntity auth = authRepo.findAuthEntityByName("ROLE_GUEST");
            data.setPwd(encoder.encode(formData.getPwd()));
            data.setAuth(auth);
            data.setPin(pin);
            data.setConfirmed(false);
            repo.save(data);
            return pin;
        }
        catch (Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }
}
