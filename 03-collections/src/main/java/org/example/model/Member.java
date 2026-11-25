package org.example.model;

import java.util.Objects;

public record Member(String id, String name) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
