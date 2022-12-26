import java.util.ArrayList;

public class Cuenta {

	/*
	 * El nombre de la cuenta.
	 */
	private String nombre;

	/*
	 * El número identificador único de la cuenta.
	 */
	private String uuid;

	/*
	 * El objeto del usuario que posee la cuenta.
	 */
	private Usuario propietario;

	/*
	 * La lista de transacciones para esta cuenta.
	 */
	private ArrayList<Transaccion> transacciones;

	/**
	 * 
	 * @param nombre      el nombre de la cuenta
	 * @param propietario el objeto Usuario propietario de la cuenta
	 * @param banco       el objeto Banco que emite la cuenta
	 */
	public Cuenta(String nombre, Usuario propietario, Banco banco) {
		// // Se establece el nombre de la cuenta y el objeto del propietario
		this.nombre = nombre;
		this.propietario = propietario;

		// Se obtiene un nuevo UUID de la cuenta
		this.uuid = banco.obtenerNuevoIdentificadorDCuenta();

		// Se inicializan las transacciones
		this.transacciones = new ArrayList<Transaccion>();
	}

	/**
	 * Obtener el UUID de la cuenta
	 * 
	 * @return el uuid
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Obtener la línea de resumen de la cuenta
	 * 
	 * @return la frase del resumen
	 */
	public String getResumen() {
		// Obtener el balance de la cuenta
		double balance = this.getBalance();

		// Personalizar la línea del resumen, dependiendo si el saldo
		// es negativo o no
		if (balance >= 0) {
			return String.format("%s : %.02f € : %s", this.uuid, balance, this.nombre);
		} else {
			return String.format("%s : (%.02f) € : %s", this.uuid, balance, this.nombre);
		}
	}

	/**
	 * Obtener el balance de la cuenta sumando las cantidades de las transacciones
	 * 
	 * @return el valor del balance
	 */
	public double getBalance() {
		double balance = 0;
		for (Transaccion transaccion : this.transacciones) {
			balance += transaccion.getCantidad();
		}

		return balance;
	}

	/**
	 * Pintar el historial de transacciones de la cuenta
	 */
	public void pintarHistorialDTransacciones() {
		System.out.printf("\nHistorial de transacciones de la cuenta %s\n", this.uuid);
		for (int t = this.transacciones.size() - 1; t >= 0; t--) {
			System.out.println(this.transacciones.get(t).getResumen());
		}
		System.out.println();
	}

	/**
	 * Se inserta una nueva transacción a la cuenta
	 * 
	 * @param cantidad    la cantidad transferida
	 * @param anotaciones las anotaciones de la transacción
	 */
	public void insertarTransaccion(double cantidad, String anotaciones) {
		// Se crea un nuevo objeto Transaccion y se añade a la lista
		Transaccion nuevaTransaccion = new Transaccion(cantidad, anotaciones, this);
		this.transacciones.add(nuevaTransaccion);
	}
}
