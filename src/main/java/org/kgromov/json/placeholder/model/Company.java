package org.kgromov.json.placeholder.model;

/**
 * Represents company information for a user.
 *
 * @param name        The name of the company
 * @param catchPhrase The company's catchphrase or slogan
 * @param bs          The business services or description
 */
public record Company(
        String name,
        String catchPhrase,
        String bs
) {
}
