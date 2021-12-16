package com.company.interfaces;

public interface BasicOperations<T> {
    void add(T item);
    void remove(T item);
    void changeItem(T currentItem, T newItem);
    T search(T item);
    T searchByName(String name);
    T searchById(int id);
}
