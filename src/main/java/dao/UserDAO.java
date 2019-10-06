package dao;

import model.User;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO extends AbstractDao {
    public void saveUser(User user) {
        hibernateUtil.save(user);
    }

    //możemy albo przekazać samo Id albo usera"
    public void deleteUser(long userId) {
        hibernateUtil.delete(User.class, userId);
    }

    //szukanie urzytkownika po loginie
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select  u from User u where email = :email", User.class);
        return query.setParameter("email", email).getSingleResult();
    }

    public User getUserByLogin(String login) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where login = :login", User.class);
        return query.setParameter("login", login).getSingleResult();
    }

    public List<User> getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where name = :name", User.class);
        return query.setParameter("name", name).getResultList();
    }

    //sprawdzenie czy User istnieje
    public boolean isUserExist(String login, String password) {
        Query query = entityManager.createQuery("select count(*) as cnt from User u where u.login = :login and u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        Object singleResult = query.getSingleResult();
        return ((Long) singleResult > 0) ? true : false;
    }

    public List<User> getFollowedUser(String login) {
        User user = getUserByLogin(login);
        Long userId = user.getId();
        Query query = entityManager.createQuery("select distinct follows from User u where u.id = :userId");
        return query.setParameter("userId", userId).getResultList();
    }

    public List<User> getNotFollowedUsers(String login) {
        User user = getUserByLogin(login);
        Query query = entityManager.createQuery("select u from User u where u.login != :login");
        query.setParameter("login", login);
        //TODO implement uery with left join relation to follows_followed table
        List<User> users = query.getResultList();
        List<User> followedUsers = getFollowedUser(login);
        users.removeAll(followedUsers);
        return users;
    }

    public void follow(String currentUserLogin, String userLoginToFollow) {
        if (currentUserLogin != userLoginToFollow) {
            User currentUser = getUserByLogin(currentUserLogin);
            User userToFollow = getUserByLogin(userLoginToFollow);
            currentUser.getFollows().add(userToFollow);
            saveUser(currentUser);
        }
    }

    //stopFollowing
    public void stopFollowing(String currentUserLogin, String userLoginToStopFollow) {
        if (currentUserLogin != userLoginToStopFollow) {
            User currentUser = getUserByLogin(currentUserLogin);
            User userToStopFollowing = getUserByLogin(userLoginToStopFollow);
            currentUser.getFollows().remove(userToStopFollowing);
            saveUser(currentUser);
        }

    }

}
