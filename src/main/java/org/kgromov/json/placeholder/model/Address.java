package org.kgromov.json.placeholder.model;

/**
 * Represents a physical address with geographic coordinates.
 * This is a value object used within the User model.
 * 
 * @param street The street address
 * @param suite Apartment or suite number
 * @param city The city name
 * @param zipcode The postal/zip code
 * @param geo Geographic coordinates (latitude and longitude)
 */
public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo
) {}
