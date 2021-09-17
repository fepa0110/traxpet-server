package model;

public enum Estado{
    INACTIVA,
    ACTIVA,
    ENCONTRADA;

    public String toDbValue() {
        return this.name().toLowerCase();
    }

    public static Estado from(String estado) {
        return Estado.valueOf(estado.toUpperCase());
    }
}