package co.inventario.dto.globalDto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}