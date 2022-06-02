package ru.yandex.practicum.filmorate.storage;

public abstract class AbstractInMemoryStorage {
    private int index = 0;

    protected int getNextIndex() {
        index += 1;
        return index;
    }
}
