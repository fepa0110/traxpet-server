package model;

import model.Estado;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, String> {
    @Override
    public String convertToDatabaseColumn(Estado estado) {
        return estado.toDbValue();
    }

    @Override
    public Estado convertToEntityAttribute(String dbData) {
        return Estado.from(dbData);
    }
}