package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getCommonFriends(UUID userId, UUID otherUserId) {
        User user = userStorage.get(userId).orElseThrow(IllegalArgumentException::new);
        User otherUser = userStorage.get(otherUserId).orElseThrow(IllegalArgumentException::new);

        Set<User> userFriends = user.getFriends();
        Set<User> otherUserFriends = otherUser.getFriends();

        return userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toList());
    }

    public void addFriend(UUID userId, UUID friendId) {
        User user = userStorage.get(userId).orElseThrow(IllegalArgumentException::new);
        User friend = userStorage.get(friendId).orElseThrow(IllegalArgumentException::new);

        addFriendToUser(user, friend);
        addFriendToUser(friend, user);
    }

    public void removeFriend(UUID userId, UUID friendId) {
        User user = userStorage.get(userId).orElseThrow(IllegalArgumentException::new);
        User friend = userStorage.get(friendId).orElseThrow(IllegalArgumentException::new);

        deleteFriendFromUser(user, friend);
        deleteFriendFromUser(friend, user);
    }

    private void addFriendToUser(User user, User friend) {
        Set<User> friends = user.getFriends();
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
        user.setFriends(friends);
    }

    private void deleteFriendFromUser(User user, User friend) {
        Set<User> friends = user.getFriends();
        if (friends == null)
            return;
        friends.remove(user);
    }
}
