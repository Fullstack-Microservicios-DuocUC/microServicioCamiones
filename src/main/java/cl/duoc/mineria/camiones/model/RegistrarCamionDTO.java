package cl.duoc.mineria.camiones.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrarCamionDTO {

    // La patente es obligatoria
    @NotBlank(message = "La patente no puede estar vacía")

    // Debe tener entre 6 y 10 caracteres
    @Size(
        min = 6,
        max = 10,
        message = "La patente debe tener entre 6 y 10 caracteres"
    )

    // Valida formatos como:
    // ABCD12
    // BBCC-22
    // Se aceptan mayúsculas y minúsculas.
    @Pattern(
        regexp = "^[A-Za-z]{4}[0-9]{2}$|^[A-Za-z]{2}[A-Za-z]{2}-[0-9]{2}$",
        message = "Formato de patente inválido. Ejemplo válido: ABCD12 o BBCC-22"
    )
    private String patente;

    // Campo obligatorio
    @NotNull(message = "La capacidad de tonelaje es obligatoria")

    // Debe ser un número positivo
    @Positive(message = "La capacidad debe ser mayor que cero")

    // Capacidad mínima permitida
    @DecimalMin(
        value = "20",
        message = "La capacidad mínima permitida es de 20 toneladas"
    )

    // Capacidad máxima permitida
    @DecimalMax(
        value = "500",
        message = "La capacidad máxima permitida es de 500 toneladas"
    )
    private Double capacidadTonelaje;

}