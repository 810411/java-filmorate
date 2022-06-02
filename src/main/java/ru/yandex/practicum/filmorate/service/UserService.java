package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Set<User> getUserFriends(int userId) {
        User user = userStorage.get(userId).orElseThrow(NotFoundException::new);
        Set<Integer> userFriendsIdSet = user.getFriends();

        if (userFriendsIdSet == null) {
            return new HashSet<>();
        }

        return userFriendsIdSet.stream()
                .map(id ->
                        userStorage.get(id).orElseThrow(NotFoundException::new)
                ).collect(Collectors.toSet());
    }

    public Set<User> getCommonFriends(int userId, int otherUserId) {
        Set<User> userFriends = getUserFriends(userId);
        Set<User> otherUserFriends = getUserFriends(otherUserId);

        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toSet());
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.get(userId).orElseThrow(NotFoundException::new);
        User friend = userStorage.get(friendId).orElseThrow(NotFoundException::new);

        addFriendToUser(user, friend);
        addFriendToUser(friend, user);
    }

    public void removeFriend(int userId, int friendId) {
        User user = userStorage.get(userId).orElseThrow(NotFoundException::new);
        User friend = userStorage.get(friendId).orElseThrow(NotFoundException::new);

        deleteFriendFromUser(user, friend);
        deleteFriendFromUser(friend, user);
    }

    private void addFriendToUser(User user, User friend) {
        Set<Integer> friends = user.getFriends();
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend.getId());
        user.setFriends(friends);
    }

    private void deleteFriendFromUser(User user, User friend) {
        Set<Integer> friends = user.getFriends();
        if (friends == null)
            return;
        friends.remove(friend.getId());
    }
}
