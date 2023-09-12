package com.example.tv360.presenter;
import com.example.tv360.Interface.LoginInterface;
import com.example.tv360.model.DataObjectLogin;
import com.example.tv360.model.FilmModel;
import com.example.tv360.model.HomeModel;
import com.example.tv360.model.LoginModel;

import java.util.List;

public class LoginPresenter {

    private LoginInterface mloginInterface;

    public LoginPresenter(LoginInterface mloginInterface) {
        this.mloginInterface = mloginInterface;
    }

    public  void  login(DataObjectLogin userModel)
    {
        if(userModel.getErrorCode() == 200)
        {
            mloginInterface.loginSuccess(userModel.getData().getAccessToken(),userModel.getData().getRefreshToken(),userModel.getData().getUserId(),userModel.getData().getProfileid());
        }
        else
        {
            mloginInterface.loginError();
        }
    }


}

