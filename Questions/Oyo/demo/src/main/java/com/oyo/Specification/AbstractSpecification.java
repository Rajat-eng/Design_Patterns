package com.oyo.Specification;

public abstract class AbstractSpecification<T> implements Specification<T> {
    @Override
    public Specification<T> and(Specification<T> other) {
        return new AndSpecification<>(this, other);
    }
}
