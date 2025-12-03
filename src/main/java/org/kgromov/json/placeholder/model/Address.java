package org.kgromov.json.placeholder.model;

/**
 * Address model - value object for User model
 */
public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {}
