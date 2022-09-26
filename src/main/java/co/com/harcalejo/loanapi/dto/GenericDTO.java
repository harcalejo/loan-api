package co.com.harcalejo.loanapi.dto;

/**
 * Esta clase nos permite definir un template para los objetos tipo DTO,
 * con el fin de manejar un estandar en su definición.
 *
 * @param <T> define la entidad raiz del DTO
 */
public abstract class GenericDTO <T> {

    /**
     * Método que permite generar la entidad raíz a partir los atributos
     * definidos en el DTO.
     *
     * @return la entidad raíz del DTO
     */
    abstract T toEntity();
}
