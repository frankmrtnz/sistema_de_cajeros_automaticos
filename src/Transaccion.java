import java.util.Date;

public class Transaccion {

	/**
	 * La cantidad de la transacci�n.
	 */
	private double cantidad;

	/**
	 * La hora y fecha de la transacci�n.
	 */
	private Date timestamp;

	/**
	 * Anotaciones para la transacci�n.
	 */
	private String anotaciones;

	/**
	 * La cuenta donde la transacci�n se ha realizado.
	 */
	private Cuenta cuenta;

	/**
	 * Crea una nueva transacci�n
	 * 
	 * @param cantidad la cantidad de la transacci�n
	 * @param cuenta   la cuenta donde se ha hecho la transacci�n
	 */
	public Transaccion(double cantidad, Cuenta cuenta) {
		this.cantidad = cantidad;
		this.cuenta = cuenta;
		this.timestamp = new Date();
		this.anotaciones = "";
	}

	/**
	 * Crea una nueva transacci�n
	 * 
	 * @param cantidad    la cantidad de la transacci�n
	 * @param anotaciones anotaciones para la transacci�n
	 * @param cuenta      la cuenta donde se ha hecho la transacci�n
	 */
	public Transaccion(double cantidad, String anotaciones, Cuenta cuenta) {
		// Se invoca al constructor con dos argumentos
		this(cantidad, cuenta);

		// Se setean las anotaciones
		this.anotaciones = anotaciones;
	}

	/**
	 * Obtener la cantidad de la transacci�n
	 * 
	 * @return la cantidad
	 */
	public double getCantidad() {
		return this.cantidad;
	}

	/**
	 * Obtener una frase que resume la transacci�n
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
