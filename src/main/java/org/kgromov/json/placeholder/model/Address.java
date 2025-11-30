package org.kgromov.json.placeholder.model;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {}
