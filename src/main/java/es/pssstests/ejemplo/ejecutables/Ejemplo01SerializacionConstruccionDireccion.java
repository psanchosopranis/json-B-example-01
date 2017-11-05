package es.pssstests.ejemplo.ejecutables;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyNamingStrategy;

import es.pssstests.ejemplo.componentes.Direccion;


public class Ejemplo01SerializacionConstruccionDireccion {

	public static void main(String[] args) {
			
		try {
			
		    // Custom configuration
	        JsonbConfig config = new JsonbConfig()
	            .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_DASHES)
	            .withEncoding("UTF-8")
	            .withBinaryDataStrategy(BinaryDataStrategy.BASE_64)
	            .withFormatting(true);

	        Jsonb jsonb = JsonbBuilder.create(config);
			
			Direccion direccion = new Direccion();
			direccion.setLinea1("Martínez Villergas, 52. Edificio B - Planta 6");
			direccion.setLocalidad("MADRID");
			direccion.setCodigoPostal("28027");
			direccion.setProvincia("MADRID");
			direccion.setTelefono1("+34 91 556 0013");
			
			System.out.println("\n===================================================");
			System.out.println("Un ejemplo de Objeto 'Direccion':");
			System.out.println("===================================================\n");
			System.out.println(direccion.toString());

			String direccionAsJSONString = jsonb.toJson(direccion);
			
			System.out.println("\n===================================================");
			System.out.println("Serializado como String JSON:");		
			System.out.println("=============================================\n");			
			System.out.println(direccionAsJSONString);
			System.out.println("\nNota: Puede observarse:");
			System.out.println("\t1) Ausencia de Serialización de elementos con valor 'null'");
			System.out.println("\t2) JSON-B ha optado por serializar los atributos por orden alfabético de su nombre");

			System.out.println("\n===================================================");
			System.out.println("Usamos ahora un String JSON representativo del");
			System.out.println("mismo objeto de direccion, SIN informar los");	
			System.out.println("campos a los que corresponde un valor 'null': ");
			System.out.println("===================================================\n");	
			
			direccionAsJSONString =
					"{"
					+"\"codigo-postal\": \"28027\","
					+"\"linea1\": \"Martínez Villergas, 52. Edificio B - Planta 6\","
					+"\"localidad\": \"MADRID\","
					+"\"provincia\": \"MADRID\","
					+"\"telefono1\": \"+34 91 556 0013\""
					+"}";

			System.out.println(direccionAsJSONString);
						
			direccion = (Direccion) jsonb.fromJson(direccionAsJSONString, Direccion.class);
 
			
			System.out.println("\n===================================================");
			System.out.println("El ejemplo de Objeto 'Direccion' resultante es:");		
			System.out.println("=============================================\n");	
			System.out.println(direccion.toString());
			System.out.println("\nNota: Puede observarse cómo:");
			System.out.println(
					"Los 'tags' ausentes inicializan los elementos correspondientes"
					+ "\ncon valor 'null' al construir el objeto.");
						
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}
	
}
	
	



