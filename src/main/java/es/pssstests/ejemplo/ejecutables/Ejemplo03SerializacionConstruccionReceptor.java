package es.pssstests.ejemplo.ejecutables;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyNamingStrategy;

import es.pssstests.ejemplo.componentes.Direccion;
import es.pssstests.ejemplo.componentes.PersonaFisicaOJuridica;
import es.pssstests.ejemplo.componentes.Producto;
import es.pssstests.ejemplo.componentes.Receptor;

public class Ejemplo03SerializacionConstruccionReceptor {

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

			Receptor receptor = new Receptor(
					"Juan Hernández Pérez-Tuñón", 
					PersonaFisicaOJuridica.F, 
					"22222222X", 
					direccion);
			
			System.out.println("\n===================================================");
			System.out.println("Un ejemplo de Objeto 'Receptor':");
			System.out.println("===================================================\n");
			System.out.println(receptor.toString());

			String receptorAsJSONString = jsonb.toJson(receptor);
			
			System.out.println("\n===================================================");
			System.out.println("Serializado como String JSON:");		
			System.out.println("===================================================\n");			
			System.out.println(receptorAsJSONString);
			System.out.println("Nota: Puede observarse:");
			System.out.println("\t1) Ausencia de Serialización de elementos con valor 'null'");
			System.out.println("\t2) Serialización transparente del 'enum' Persona Física o Jurídica");
			System.out.println("\t3) Serialización anidada del objeto de 'direccion'");
			System.out.println("\t4) JSON-B ha optado por serializar los atributos por orden alfabético de su nombre");
			System.out.println("\t5) Nótese el uso de PropertyNamingStrategy.LOWER_CASE_WITH_DASHES");
			
			System.out.println("\n===================================================");
			System.out.println("Usamos ahora un archivo JSON representativo del");
			System.out.println("mismo objeto de Receptor");	
			System.out.println("===================================================\n");	
			System.out.println("Notas:");
			System.out.println("\t1) El archivo JSON recuperado como recurso se encuentra en:");
			System.out.println("\t   resources/ejemplos_json/recurso01.json");
			System.out.println("\t2) Su encoding es 'UTF-8'.");

			String archivoJSONComoRecurso = "/ejemplos_json/recurso01.json";
			
			receptor = (Receptor) jsonb.fromJson(
					ClassLoader.class.getResourceAsStream(archivoJSONComoRecurso), 
					Receptor.class);			

			System.out.println("\n===================================================");
			System.out.println("El ejemplo de Objeto 'Receptor' resultante es:");		
			System.out.println("===================================================\n");	
			System.out.println(receptor.toString());
			System.out.println("\nNota: Puede observarse cómo:");
			System.out.println(
					"Los 'tags' ausentes inicializan los elementos correspondientes"
					+ "\ncon valor 'null' al construir el objeto.");			
			

			System.out.println("\n===================================================");
			System.out.println("Veamos que ocurre cuando utilizamos un valor para");
			System.out.println("'Persona Física o Jurídica' como 'X' que no forma");
			System.out.println("parte de la enumeración 'F,J':");
			System.out.println("===================================================\n");
			System.out.println("Notas:");
			System.out.println("\t1) El archivo JSON recuperado como recurso se encuentra en:");
			System.out.println("\t   resources/ejemplos_json/recurso02.json");
			System.out.println("\t2) Su encoding es 'UTF-8'.");
			
			archivoJSONComoRecurso = "/ejemplos_json/recurso02.json";
			
			receptor = (Receptor) jsonb.fromJson(
					ClassLoader.class.getResourceAsStream(archivoJSONComoRecurso), 
					Receptor.class);			

			System.out.println("\n===================================================");
			System.out.println("El ejemplo de Objeto 'Receptor' resultante es:");		
			System.out.println("===================================================\n");	
			System.out.println(receptor.toString());
			System.out.println("\nNota: Puede observarse cómo:");	
			System.out.println("\t1) La propiedad de 'Persona Física o Jurídica' (pFisJur)");
			System.out.println("\t   ha quedado con valor NULO <null>");
			System.out.println("\t2) Los 'tags' ausentes inicializan los elementos correspondientes");
		    System.out.println("\t   con valor 'null' al construir el objeto.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
