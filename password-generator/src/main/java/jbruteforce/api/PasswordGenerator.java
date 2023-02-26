package jbruteforce.api;

import java.util.Optional;

public interface PasswordGenerator {

    /**
     * Sets a pattern to follow for password generation.
     * <p>
     * Possible tokens:
     * <ul>
     * <li>C - upper-case character (61 to 91 ascii, inclusive)</li>
     * <li>c - lower-case character (97 to 123 ascii. inclusive)</li>
     * <li>0 - number from 0 to 9</li>
     * </ul>
     * 
     * Known characters are escaped with double quotes, like
     * "H"Cc00"L""x"
     * 
     * @param pattern a string consisting of tokens
     */
    public void setPattern(String pattern);

    /**
     * 
     * @return the next password of the sequence that satisfies the specified pattern,
     * which has not been returned yet
     */
    public Optional<String> nextPassword();
    
}
