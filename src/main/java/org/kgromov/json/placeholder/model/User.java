package org.kgromov.json.placeholder.model;

public record User(
        long id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
    public record Company(
            String name,
            String catchPhrase,
            String bs
    ) {}
}
