package com.example.tv360.presenter;
import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.UserModel;

import java.util.List;

public class LoginPresenter {

    private LoginInterface mloginInterface;

    public LoginPresenter(LoginInterface mloginInterface) {
        this.mloginInterface = mloginInterface;
    }

    public  void  login(UserModel userModel)
    {
        if(userModel.getUsername().equals("a") && userModel.getPassword().equals("a"))
        {
            mloginInterface.loginSuccess();
        }
        else
        {
            mloginInterface.loginError();
        }
    }


}

