package co.com.harcalejo.loanapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Bean de configuracion usado para el mapeo de constantes y carga de
 * properties asociadas a las reglas y comportamientos segun el negocio
 *
 * @author Hugo Alejandro Rodriguez
 * @version 1.0.0
 */
@Configuration
@PropertySource("loan.properties")
@Getter
public class LoanProperties {

    /**
     * Constante con el identificador unico que representa los nuevos
     * usuarios
     */
    public static final int NEW_TARGET = 1;

    /**
     * Constante con el identificador unico que representa los usuarios
     * frecuentes
     */
    public static final int FREQUENT_TARGET = 2;

    /**
     * Constante con el identificador único que representa los usuarios
     * premium
     */
    public static final int PREMIUM_TARGET = 3;

    /**
     * Constante que representa el número de meses del año
     */
    public static final int MONTHS = 12;

    /**
     * Propiedad que representa el rate asociado a un usuario nuevo
     */
    @Value("${target.new.rate}")
    private double newRate;

    /**
     * Propiedad que representa el rate asociado a un usuario frecuente
     */
    @Value("${target.frequent.rate}")
    private double frequentRate;

    /**
     * Propiedad que representa el rate asociado a un usuario premium
     */
    @Value("${target.premium.rate}")
    private double premiumRate;

    /**
     * Propiedad que representa el monto máximo por solicitud asociado
     * a un usuario nuevo
     */
    @Value("${target.new.max_amount}")
    private double newMaxAmount;

    /**
     * Propiedad que representa el monto máximo por solicitud asociado
     * a un usuario nuevo
     */
    @Value("${target.frequent.max_amount}")
    private double frequentMaxAmount;

    /**
     * Propiedad que representa el monto máximo por solicitud asociado
     * a un usuario nuevo
     */
    @Value("${target.premium.max_amount}")
    private double premiumMaxAmount;
}
