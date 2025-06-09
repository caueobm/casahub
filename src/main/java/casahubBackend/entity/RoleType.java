package casahubBackend.entity;

public enum RoleType {
    ROLE_USER,
    ROLE_CORRETOR,
    ROLE_ADMIN;


    @Override
    public String toString() {
        return name();
    }
}