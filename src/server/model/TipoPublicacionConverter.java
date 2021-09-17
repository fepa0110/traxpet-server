package model;

import model.TipoPublicacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoPublicacionConverter implements AttributeConverter<TipoPublicacion, String> {
    @Override
    public String convertToDatabaseColumn(TipoPublicacion tipoPublicacion) {
        return tipoPublicacion.toDbValue();
    }

    @Override
    public TipoPublicacion convertToEntityAttribute(String dbData) {
        return TipoPublicacion.from(dbData);
    }
}