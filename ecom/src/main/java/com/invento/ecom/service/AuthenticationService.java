package com.invento.ecom.service;

import com.invento.ecom.model.User;
import com.invento.ecom.viewModel.LoginRequest;
import com.invento.ecom.viewModel.RegistrationRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationService {
    private static List<User> userList = new ArrayList<User>();

    @PostConstruct
    public void loadInitialData(){
        User user = new User();
        user.setUserID(1);
        user.setName("Shubham");
        user.setEmail("Shubham@gmail.com");
        user.setUserName("admin");
        user.setPassword("admin@123");
        user.setPhoneNumber("80556978222");
        user.setUserType(1);

        User user2 = new User();
        user2.setUserID(2);
        user2.setName("Daniel");
        user2.setEmail("daniel@gmail.com");
        user2.setUserName("daniel");
        user2.setPassword("daniel@123");
        user2.setPhoneNumber("8785455222");
        user2.setUserType(2);


        userList.add(user);
        userList.add(user2);
    }

    public User authenticateUser(LoginRequest loginRequest){
        User user = null;
        if(loginRequest!=null && loginRequest.getUserName()!=null && loginRequest.getPassword()!=null){
           for(User memUser:userList){
                if(memUser.getUserName()!=null && memUser.getUserName().equalsIgnoreCase(loginRequest.getUserName()) && memUser.getPassword()!=null && memUser.getPassword().equalsIgnoreCase(loginRequest.getPassword())){
                    user = memUser;
                    break;
                }
           }
        }
        return user;
    }

    public User registerUser(RegistrationRequest registrationRequest){
        User user = null;
        if(registrationRequest!=null && registrationRequest.getUserName()!=null && registrationRequest.getPassword()!=null && registrationRequest.getName()!=null && registrationRequest.getEmail()!=null && registrationRequest.getPhoneNumber()!=null){
            user = new User();
            user.setUserID(userList.size()+1);
            user.setUserName(registrationRequest.getUserName());
            user.setPassword(registrationRequest.getPassword());
            user.setName(registrationRequest.getName());
            user.setPhoneNumber(registrationRequest.getPhoneNumber());
            user.setEmail(registrationRequest.getEmail());
            user.setUserType(2);

            userList.add(user);
        }
        return user;
    }

    public List<User> getUserList() {
        return userList;
    }
}
