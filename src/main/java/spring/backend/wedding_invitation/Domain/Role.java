package spring.backend.wedding_invitation.Domain;

public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    // "USER", "ADMIN"
    private String value;

    // Constructor 생성자
    Role(String value) {
        this.value = value;
    }

    // GetValue
    public String getValue() {
        return this.value;
    }
}
