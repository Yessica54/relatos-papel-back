package com.unir.grupo_12.ms_books_catalogue.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

public class EntityUpdater {

    public static <T> void updateNonNullFields(T source, T target) {
        // Obtener descriptores de propiedades del objeto fuente
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            try {
                // Método getter y setter para cada propiedad
                Method readMethod = descriptor.getReadMethod();
                Method writeMethod = descriptor.getWriteMethod();

                if (readMethod != null && writeMethod != null) {
                    // Obtener el valor de la propiedad del objeto fuente
                    Object value = readMethod.invoke(source);

                    // Si el valor no es null, actualizar la propiedad en el objeto destino
                    if (value != null) {
                        writeMethod.invoke(target, value);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Error updating fields: " + descriptor.getName(), e);
            }
        }
    }
}
