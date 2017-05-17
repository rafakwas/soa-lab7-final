package controller;

import data.BookRepositoryRemote;
import model.Book;
import model.Pair;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
@Named("registrationbean")
public class RegistrationBean implements Serializable{
    private final static Logger LOGGER = Logger.getLogger(RegistrationBean.class.toString());

    @EJB
    BookRepositoryRemote bookRepositoryRemote;

    private String login;
    private String password;
    private boolean notifications;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public String submit() {
        LOGGER.info(()-> "new user: " + login + "," + password + "," + notifications);
        bookRepositoryRemote.addUser(login,password,notifications);
        return "login";
    }
}
