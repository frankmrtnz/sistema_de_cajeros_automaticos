import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		// Se inicializa Scanner
		Scanner scanner = new Scanner(System.in);

		// Se inicializa Banco
		Banco banco = new Banco("Banco de Madrid");

		// Se añade un usuario, el cual también crea una cuenta de ahorros
		Usuario usuario = banco.insertarUsuario("Francisco", "Martinez Martin", "1234");

		// Se agrega una nueva cuenta corriente para el usuario
		Cuenta nuevaCuenta = new Cuenta("Corriente", usuario, banco);
		usuario.insertarCuenta(nuevaCuenta);
		banco.insertarCuenta(nuevaCuenta);

		Usuario usuarioActual;
		while (true) {
			// Permanece con el indicador de inicio de sesión hasta que se
			// inicie sesión correctamente
			usuarioActual = ATM.permanecerEnMenuPrincipal(banco, scanner);

			// Permanece en el menú principal hasta que el usuario salga
			ATM.pintarMenuDUsuario(usuarioActual, scanner);
		}
	}

	/**
	 * Se pinta el menú de login del ATM
	 * 
	 * @param banco   el objeto Banco con sus cuentas a usar
	 * @param scanner el objeto Scanner para las entradas de usuario
	 * @return el objeto Usuario autenticado
	 */
	public static Usuario permanecerEnMenuPrincipal(Banco banco, Scanner scanner) {
		// Inicializaciones
		String idUsuario;
		String pin;
		Usuario usuarioAutenticado;

		// Solicitar al usuario la combinación correcta de ID de usuario y pin
		// hasta que se introduzca una correcta
		do {
			System.out.printf("\n\nBienvenido a %s\n\n", banco.getNombre());
			System.out.print("Introduzca el ID de usuario: ");
			idUsuario = scanner.nextLine();
			System.out.print("Introduzca el pin: ");
			pin = scanner.nextLine();

			// Intentar obtener el objeto Usuario correspondiente a la
			// combinación de ID y pin obtenidos
			usuarioAutenticado = banco.loginUsuario(idUsuario, pin);
			if (usuarioAutenticado == null) {
				System.out.println("Combinación de ID y pin incorrecta." + " Por favor, inténtelo de nuevo.");
			}
		} while (usuarioAutenticado == null); // Se continua iterando hasta conseguir un login correcto

		return usuarioAutenticado;
	}

	/**
	 * 
	 * @param usuario
	 * @param scanner
	 */
	public static void pintarMenuDUsuario(Usuario usuario, Scanner scanner) {
		// Se pinta un resumen de las cuentas del usuario
		usuario.pintarResumenDCuentas();

		// Inicializaciones
		int opcionSeleccionada;

		// Menu de usuario
		do {
			System.out.printf("Bienvenido %s, ¿qué le gustaría hacer?\n", usuario.getNombre());
			System.out.println("1) Consultar historial de transacciones de la cuenta");
			System.out.println("2) Retirar");
			System.out.println("3) Depositar");
			System.out.println("4) Transferir");
			System.out.println("5) Salir");
			System.out.println();
			System.out.print("Elija una opción: ");
			opcionSeleccionada = scanner.nextInt();

			if (opcionSeleccionada < 1 || opcionSeleccionada > 5) {
				System.out
						.println("La opción seleccionada no es correcta. Por favor introduzca una opción válida 1-5.");
			}
		} while (opcionSeleccionada < 1 || opcionSeleccionada > 5);

		// Se procesa la opción seleccionada
		switch (opcionSeleccionada) {
		case 1:
			ATM.consultarHistorialDTransacciones(usuario, scanner);
			break;
		case 2:
			ATM.retirarFondos(usuario, scanner);
			break;
		case 3:
			ATM.depositarFondos(usuario, scanner);
			break;
		case 4:
			ATM.transferirFondos(usuario, scanner);
			break;
		case 5:
			// Se machaca la entrada anterior
			scanner.nextLine();
			break;
		}

		// Se muestra este menú a menos que el usuario quiera salir
		if (opcionSeleccionada != 5) {
			ATM.pintarMenuDUsuario(usuario, scanner);
		}
	}

	/**
	 * Consultar el historial de transacciones de una cuenta
	 * 
	 * @param usuario el objeto Usuario logueado
	 * @param scanner el objeto Scanner usado para la entrada del usuario
	 */
	public static void consultarHistorialDTransacciones(Usuario usuario, Scanner scanner) {
		int numCuenta;

		// Se obtiene la cuenta de la cual se quiere consultar el historial de
		// transacciones
		do {
			System.out.printf(
					"Introduzca el número (1-%d) de la cuenta\n"
							+ "de la cual se quiere consultar el historial de transacciones: ",
					usuario.getNumeroDCuentas());
			numCuenta = scanner.nextInt() - 1;
			if (numCuenta < 0 || numCuenta >= usuario.getNumeroDCuentas()) {
				System.out.println("Cuenta no válida. Por favor, inténtelo de nuevo.");
			}
		} while (numCuenta < 0 || numCuenta >= usuario.getNumeroDCuentas());

		// Se pinta el historial de transacciones
		usuario.pintarHistorialDTransaccionesDCuenta(numCuenta);
	}

	/**
	 * Hacer el proceso de transferencia de fondos de una cuenta a otra
	 * 
	 * @param usuario el objeto Usuario logueado
	 * @param scanner el objeto Scanner usado para la entrada del usuario
	 */
	public static void transferirFondos(Usuario usuario, Scanner scanner) {
		// Inicializaciones
		int numCuentaEmisora;
		int numCuentaDestino;
		double cantidad;
		double balanceDCuenta;

		// Se obtiene la cuenta emisora
		do {
			System.out.printf("Introduzca el número (1-%d) de la cuenta emisora: ", usuario.getNumeroDCuentas());
			numCuentaEmisora = scanner.nextInt() - 1;
			if (numCuentaEmisora < 0 || numCuentaEmisora >= usuario.getNumeroDCuentas()) {
				System.out.println("Cuenta no válida. Por favor, inténtelo de nuevo.");
			}
		} while (numCuentaEmisora < 0 || numCuentaEmisora >= usuario.getNumeroDCuentas());

		balanceDCuenta = usuario.getBalanceDCuenta(numCuentaEmisora);

		// Se obtiene la cuenta de destino
		do {
			System.out.printf("Introduzca el número (1-%d) de la cuenta de destino: ", usuario.getNumeroDCuentas());
			numCuentaDestino = scanner.nextInt() - 1;
			if (numCuentaDestino < 0 || numCuentaDestino >= usuario.getNumeroDCuentas()) {
				System.out.println("Cuenta no válida. Por favor, inténtelo de nuevo.");
			}
		} while (numCuentaDestino < 0 || numCuentaDestino >= usuario.getNumeroDCuentas());

		// Se obtiene la cantidad a transferir
		do {
			System.out.printf("Introduzca la cantidad a transferir (Máx. $%.02f): $", balanceDCuenta);
			cantidad = scanner.nextDouble();
			if (cantidad < 0) {
				System.out.println("La cantidad debe ser mayor de cero.");
			} else if (cantidad > balanceDCuenta) {
				System.out.printf("La cantidad no debe ser mayor al balance de $%.02f.\n", balanceDCuenta);
			}
		} while (cantidad < 0 || cantidad > balanceDCuenta);

		// Finalmente, se hace la transferencia
		usuario.insertarTransaccionACuenta(numCuentaEmisora, -1 * cantidad,
				String.format("Se ha hecho una transferencia a la cuenta %s", usuario.getUUIDCuenta(numCuentaDestino)));
		usuario.insertarTransaccionACuenta(numCuentaDestino, cantidad,
				String.format("Se ha hecho una transferencia a la cuenta %s", usuario.getUUIDCuenta(numCuentaEmisora)));

	}

	/**
	 * Procesar un retiro de fondos de una cuenta
	 * 
	 * @param usuario el objeto Usuario logueado
	 * @param scanner el objeto Scanner usado para la entrada del usuario
	 */
	public static void retirarFondos(Usuario usuario, Scanner scanner) {
		// Inicializaciones
		int numCuentaEmisora;
		double cantidad;
		double balanceDCuenta;
		String anotaciones;

		// Se obtiene la cuenta de la que se va a retirar los fondos
		do {
			System.out.printf("Introduzca el número (1-%d) de la cuenta a retirar: ", usuario.getNumeroDCuentas());
			numCuentaEmisora = scanner.nextInt() - 1;
			if (numCuentaEmisora < 0 || numCuentaEmisora >= usuario.getNumeroDCuentas()) {
				System.out.println("Cuenta no válida. Por favor, inténtelo de nuevo.");
			}
		} while (numCuentaEmisora < 0 || numCuentaEmisora >= usuario.getNumeroDCuentas());

		balanceDCuenta = usuario.getBalanceDCuenta(numCuentaEmisora);

		// Se obtiene la cantidad a transferir
		do {
			System.out.printf("Introduzca la cantidad a retirar (Máx. $%.02f): $", balanceDCuenta);
			cantidad = scanner.nextDouble();
			if (cantidad < 0) {
				System.out.println("La cantidad debe ser mayor de cero.");
			} else if (cantidad > balanceDCuenta) {
				System.out.printf("La cantidad no debe ser mayor al balance de $%.02f.\n", balanceDCuenta);
			}
		} while (cantidad < 0 || cantidad > balanceDCuenta);

		// Se machaca la entrada anterior
		scanner.nextLine();

		// Se obtienen las anotaciones
		System.out.print("Introduzca las anotaciones: ");
		anotaciones = scanner.nextLine();

		// Se hace el retiro
		usuario.insertarTransaccionACuenta(numCuentaEmisora, -1 * cantidad, anotaciones);
	}

	/**
	 * Procesar un deposito para la cuenta
	 * 
	 * @param usuario el objeto Usuario logueado
	 * @param scanner el objeto Scanner usado para la entrada del usuario
	 */
	public static void depositarFondos(Usuario usuario, Scanner scanner) {
		// Inicializaciones
		int numCuentaDestino;
		double cantidad;
		double balanceDCuenta;
		String anotaciones;

		// Se obtiene la cuenta de la que se va a retirar los fondos
		do {
			System.out.printf("Introduzca el número (1-%d) de la cuenta a depositar: ", usuario.getNumeroDCuentas());
			numCuentaDestino = scanner.nextInt() - 1;
			if (numCuentaDestino < 0 || numCuentaDestino >= usuario.getNumeroDCuentas()) {
				System.out.println("Cuenta no válida. Por favor, inténtelo de nuevo.");
			}
		} while (numCuentaDestino < 0 || numCuentaDestino >= usuario.getNumeroDCuentas());

		balanceDCuenta = usuario.getBalanceDCuenta(numCuentaDestino);

		// Se obtiene la cantidad a transferir
		do {
			System.out.printf("Introduzca la cantidad a depositar (Máx. $%.02f): $", balanceDCuenta);
			cantidad = scanner.nextDouble();
			if (cantidad < 0) {
				System.out.println("La cantidad debe ser mayor de cero.");
			}
		} while (cantidad < 0);

		// Se machaca la entrada anterior
		scanner.nextLine();

		// Se obtienen las anotaciones
		System.out.print("Introduzca las anotaciones: ");
		anotaciones = scanner.nextLine();

		// Se hace el retiro
		usuario.insertarTransaccionACuenta(numCuentaDestino, cantidad, anotaciones);
	}
}
