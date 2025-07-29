package br.edu.ifba.inf008.interfaces;

import java.util.Optional;

public interface IGenericDAO<T, K> {
    T save(T entity);
    T update(T entity);
    boolean delete(K id);
    Optional<T> findById(K id);
}