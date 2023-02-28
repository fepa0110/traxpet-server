package model;

public enum TipoPublicacion{
    MASCOTA_BUSCADA,
    MASCOTA_ENCONTRADA;

    public String toDbValue() {
        return this.name().toUpperCase();
    }

    public static TipoPublicacion from(String tipoPublicacion) {
        return TipoPublicacion.valueOf(tipoPublicacion.toUpperCase());
    }
}