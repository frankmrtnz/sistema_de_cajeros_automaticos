import java.util.Date;

public class Transaccion {

	/**
	 * La cantidad de la transacción.
	 */
	private double cantidad;

	/**
	 * La hora y fecha de la transacción.
	 */
	private Date timestamp;

	/**
	 * Anotaciones para la transacción.
	 */
	private String anotaciones;

	/**
	 * La cuenta donde la transacción se ha realizado.
	 */
	private Cuenta cuenta;

	/**
	 * Crea una nueva transacción
	 * 
	 * @param cantidad la cantidad de la transacción
	 * @param cuenta   la cuenta donde se ha hecho la transacción
	 */
	public Transaccion(double cantidad, Cuenta cuenta) {
		this.cantidad = cantidad;
		this.cuenta = cuenta;
		this.timestamp = new Date();
		this.anotaciones = "";
	}

	/**
	 * Crea una nueva transacción
	 * 
	 * @param cantidad    la cantidad de la transacción
	 * @param anotaciones anotaciones para la transacción
	 * @param cuenta      la cuenta donde se ha hecho la transacción
	 */
	public Transaccion(double cantidad, String anotaciones, Cuenta cuenta) {
		// Se invoca al constructor con dos argumentos
		this(cantidad, cuenta);

		// Se setean las anotaciones
		this.anotaciones = anotaciones;
	}

	/**
	 * Obtener la cantidad de la transacción
	 * 
	 * @return la cantidad
	 */
	public double getCantidad() {
		return this.cantidad;
	}

	/**
	 * Obtener una frase que resume la transacción
	 * 
	 * @return la frase resumen
	 */
	public String getResumen() {
		if (this.cantidad >= 0) {
			return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.cantidad, this.anotaciones);
		} else {
			return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.cantidad, this.anotaciones);
		}
	}
}
