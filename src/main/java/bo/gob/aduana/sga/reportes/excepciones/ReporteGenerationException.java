package bo.gob.aduana.sga.reportes.excepciones;

/**
 * Created with IntelliJ IDEA.
 * User: esalamanca
 * Date: 20-03-14
 * Time: 04:23 PM
 * Excepcion para cuando no se puede crear el Reporte.
 */
public class ReporteGenerationException extends Exception {

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ReporteGenerationException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ReporteGenerationException(String message) {
        super(message);
    }
}
