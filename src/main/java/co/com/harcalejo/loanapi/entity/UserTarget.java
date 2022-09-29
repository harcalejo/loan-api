package co.com.harcalejo.loanapi.entity;

/**
 * Clasificación de usuarios objetivo
 */
public enum UserTarget {
    NEW,
    FREQUENT,
    PREMIUM;

    public String getValue() {
        return this.toString();
    }
}
