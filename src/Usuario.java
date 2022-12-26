import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Usuario {

	/*
	 * El nombre del usuario.
	 */
	private String nombre;
	/*
	 * Los apellidos del usuario.
	 */
	private String apellidos;
	/*
	 * El número identificador único del usuario.
	 */
	private String uuid;
	/*
	 * El MD5 hash del número del pin del usuario.
	 */
	private byte[] pinHash;
	/*
	 * La lista de cuentas para este usuario.
	 */
	private ArrayList<Cuenta> cuentas;

	/**
	 * 
	 * @param nombre    el nombre del usuario
	 * @param apellidos los apellidos del usuario
	 * @param pin       el número del pin de la cuenta del usuario
	 * @param banco     el objeto Banco del que el usuario es cliente
	 */

	public Usuario(String nombre, String apellidos, String pin, Banco banco) {
		// Se setean el nombre y los apellidos del usuario
		this.nombre = nombre;
		this.apellidos = apellidos;

		// Se guarda en un MD5 hash el pin debido a motivos de seguridad
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error capturado: NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// Se obtiene un ID universal único para el usuario
		this.uuid = banco.obtenerNuevoIdentificadorDUsuario();

		// Se inicializa la lista de cuentas
		this.cuentas = new ArrayList<Cuenta>();

		// Se pinta un mensaje de log
		System.out.printf("Nuevo usuario %s, %s con ID %s se ha creado.\n", apellidos, nombre, this.uuid);
	}

	/**
	 * Se inserta una cuenta para el usuario
	 * 
	 * @param cuenta la cuenta a insertar
	 */
	public void insertarCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
	}

	/**
	 * Obtener el UUID del usuario
	 * 
	 * @return el uuid
	 */
	public String getUUID() {
		return this.uuid;
	}

	/**
	 * Se verifica si un pin dado coincide con un pin de Usuario real
	 * 
	 * @param pin el pin a comprobar
	 * @return si el pin es válido o no
	 */
	public boolean validarPin(String pin) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md5.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error capturado: NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		return false;
	}

	/**
	 * Obtener el nombre del usuario
	 * 
	 * @return el nombre del usuario
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Pintar el resumen de las cuentas del usuario
	 */
	public void pintarResumenDCuentas() {
		System.out.printf("\n\nResumen de cuentas de %s\n", this.nombre);
		for (int c = 0; c < this.cuentas.size(); c++) {
			System.out.printf("		%d) %s\n", c + 1, this.cuentas.get(c).getResumen());
		}
		System.out.println();
	}

	/**
	 * Obtener el número de cuentas del usuario
	 * 
	 * @return el número de cuentas
	 */
	public int getNumeroDCuentas() {
		return this.cuentas.size();
	}

	/**
	 * Pintar el historial de transacciones de una cuenta en concreto
	 * 
	 * @param numCuenta el index de la cuenta a usar
	 */
	public void pintarHistorialDTransaccionesDCuenta(int numCuenta) {
		this.cuentas.get(numCuenta).pintarHistorialDTransacciones();
	}

	/**
	 * Obtener el balance de una cuenta en concreto
	 * 
	 * @param numCuenta el index de la cuenta a usar
	 * @return el balance de la cuenta
	 */
	public double getBalanceDCuenta(int numCuenta) {
		return this.cuentas.get(numCuenta).getBalance();
	}

	/**
	 * Obtener el UUID de una cuenta en concreto
	 * 
	 * @param numCuenta el index de la cuenta a usar
	 * @return el UUID de la cuenta
	 */
	public String getUUIDCuenta(int numCuenta) {
		return this.cuentas.get(numCuenta).getUUID();
	}

	/**
	 * Inserta una transacción a una cuenta en particular
	 * 
	 * @param numCuenta   el index de la cuenta
	 * @param cantidad    la cantidad de la transacción
	 * @param anotaciones las anotaciones de la transacción
	 */
	public void insertarTransaccionACuenta(int numCuenta, double cantidad, String anotaciones) {
		this.cuentas.get(numCuenta).insertarTransaccion(cantidad, anotaciones);
	}
}
