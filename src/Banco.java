import java.util.ArrayList;
import java.util.Random;

public class Banco {

	private String nombre;

	private ArrayList<Usuario> usuarios;

	private ArrayList<Cuenta> cuentas;

	/**
	 * Crea un nuevo objeto Banco con las listas de usuarios y cuentas vacías
	 * 
	 * @param nombre el nombre del Banco
	 */
	public Banco(String nombre) {
		this.nombre = nombre;
		this.usuarios = new ArrayList<Usuario>();
		this.cuentas = new ArrayList<Cuenta>();
	}

	/**
	 * Se genera un nuevo ID único universal para el usuario
	 * 
	 * @return el uuid
	 */
	public String obtenerNuevoIdentificadorDUsuario() {
		// Inicializaciones
		String uuid;
		Random rand = new Random();
		int longitud = 6;
		boolean noUnico;

		// Continua iterando hasta encontrar un ID único
		do {
			// Se genera el número
			uuid = "";
			for (int i = 0; i < longitud; i++) {
				uuid += ((Integer) rand.nextInt(10)).toString();
			}

			// Se hace una verificación para garantizar que sea único
			noUnico = false;
			for (Usuario usuario : this.usuarios) {
				if (uuid.compareTo(usuario.getUUID()) == 0) {
					noUnico = true;
					break;
				}
			}
		} while (noUnico);

		return uuid;
	}

	/**
	 * Se genera un nuevo ID único universal para la cuenta
	 * 
	 * @return el uuid
	 */
	public String obtenerNuevoIdentificadorDCuenta() {
		// Inicializaciones
		String uuid;
		Random rand = new Random();
		int longitud = 10;
		boolean noUnico;

		// Continua iterando hasta encontrar un ID único
		do {
			// Se genera el número
			uuid = "";
			for (int i = 0; i < longitud; i++) {
				uuid += ((Integer) rand.nextInt(10)).toString();
			}

			// Se hace una verificación para garantizar que sea único
			noUnico = false;
			for (Cuenta cuenta : this.cuentas) {
				if (uuid.compareTo(cuenta.getUUID()) == 0) {
					noUnico = true;
					break;
				}
			}
		} while (noUnico);

		return uuid;
	}

	/**
	 * Se inserta un objeto Cuenta en la lista de cuentas del Banco
	 * 
	 * @param cuenta el objeto Cuenta a insertar
	 */
	public void insertarCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
	}

	/**
	 * Se crea un nuevo usuario para el banco
	 * 
	 * @param nombre    el nombre del usuario
	 * @param apellidos los apellidos del usuario
	 * @param pin       el pin del usuario
	 * @return el nuevo objeto Usuario
	 */
	public Usuario insertarUsuario(String nombre, String apellidos, String pin) {
		// Se crea un nuevo objeto Usuario y se añade a la lista de usuarios
		Usuario nuevoUsuario = new Usuario(nombre, apellidos, pin, this);
		this.usuarios.add(nuevoUsuario);

		// Se crea una nueva cuenta de ahorros para el usuario y se insertan las cuentas
		// al Usuario y el Banco
		Cuenta nuevaCuenta = new Cuenta("Ahorros", nuevoUsuario, this);
		// Se añade el propietario y los bancos
		nuevoUsuario.insertarCuenta(nuevaCuenta);
		this.insertarCuenta(nuevaCuenta);

		return nuevoUsuario;
	}

	/**
	 * Obtiene el Usuario asociado a un id de usuario particular y un pin, si son
	 * válidos
	 * 
	 * @param idUsuario el UUID de el usuario a logear
	 * @param pin       el pin del usuario
	 * @return el objeto Usuario si el login ha sido correcto, o nulo si no lo es
	 */
	public Usuario loginUsuario(String idUsuario, String pin) {
		// Se busca en la lista de usuarios
		for (Usuario usuario : this.usuarios) {
			// Se comprueba que el id de usuario es correcto
			if (usuario.getUUID().compareTo(idUsuario) == 0 && usuario.validarPin(pin)) {
				return usuario;
			}
		}

		// Si no se ha encontrado el usuario o tiene un pin incorrecto
		return null;
	}

	/**
	 * Obtener el nombre del banco
	 * 
	 * @return el nombre del banco
	 */
	public String getNombre() {
		return this.nombre;
	}
}
