package dao;

import model.Tweet;
import model.User;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TweetDAO extends AbstractDao {
    UserDAO userDAO = new UserDAO();

    public void addTweet(String userLogin, String message) {
        User author = userDAO.getUserByLogin(userLogin);
        Tweet tweet = new Tweet();
        tweet.setPublishedAdd(new Date());
        tweet.setMessage(message);
        tweet.setAuthor(author);
        hibernateUtil.save(tweet);

    }

    public void deleteTweet(String userLogin, long tweetId) {
        User currentUser = userDAO.getUserByLogin(userLogin);
        Tweet tweet = entityManager.find(Tweet.class, tweetId);
        if (tweet.getAuthor().equals(currentUser)) {
            hibernateUtil.delete(Tweet.class, tweetId);

        }
    }

    public List<Tweet> getFollowedTweets(String userLogin) {
        User currentUser = userDAO.getUserByLogin(userLogin);
        Set<User> followedUsers = currentUser.getFollows();
        List<User> followedWithCurrent = new ArrayList<>(followedUsers);
        followedWithCurrent.add(currentUser);
        Query query = entityManager.createQuery("select t from Tweet t where t.author in :list");
        query.setParameter("list", followedWithCurrent);
        return query.getResultList();
    }
}