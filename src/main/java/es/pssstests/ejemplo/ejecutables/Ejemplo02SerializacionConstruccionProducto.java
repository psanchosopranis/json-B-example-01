package es.pssstests.ejemplo.ejecutables;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyNamingStrategy;

import org.apache.commons.io.IOUtils;

import es.pssstests.ejemplo.componentes.Direccion;
import es.pssstests.ejemplo.componentes.Producto;


public class Ejemplo02SerializacionConstruccionProducto {

	public static void main(String[] args) {
		
		try {
			
		    // Custom configuration
	        JsonbConfig config = new JsonbConfig()
	            .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_DASHES)
	            .withEncoding("UTF-8")
	            .withBinaryDataStrategy(BinaryDataStrategy.BASE_64)
	            .withFormatting(true);
	        
	        Jsonb jsonb = JsonbBuilder.create(config);
			
			String[] descripcion = new String[] {
					"Vaso de cristal transparente de alta resistencia a golpes y cambios bruscos de temperatura.",
					"Válido para lavavajillas.",
					"Disponible en color transparente y azul translúcido." 
			};
					
			Producto producto = new Producto(
					"dg-161034_640", 
					"Vaso de Agua boca ancha terminación recta", 
					2, 
					"PZA", 
					new BigDecimal("1.24"), 
					new BigDecimal("16.00"), 
					"/imagenes/RectanguloRojo.png",
					descripcion);

			
			System.out.println("\n===================================================");
			System.out.println("Un ejemplo de Objeto 'Producto':");
			System.out.println("===================================================\n");
			System.out.println(producto.toString());

			String productoAsJSONString = jsonb.toJson(producto);
			
			System.out.println("\n===================================================");
			System.out.println("Objeto Producto serializado como String JSON:");		
			System.out.println("===================================================\n");			
			System.out.println(productoAsJSONString);
			System.out.println("Nota: Puede observarse:");
			System.out.println("\t1) Serialización de elementos con valor binario en Base64");
			System.out.println("\t2) Serialización transparente de Arrays y tipos 'complejos' como BigDecimal");		
			System.out.println("\t3) JSON-B serializa objetos binarios (Ejemplo imágenes) en Base64");
			System.out.println("\t   al incluir la opción '.withBinaryDataStrategy(BinaryDataStrategy.BASE_64)'");				
			System.out.println("\t4) JSON-B ha optado por serializar los atributos por orden alfabético de su nombre");
			System.out.println("\t5) Nótese el uso de PropertyNamingStrategy.LOWER_CASE_WITH_DASHES");

			System.out.println("\n===================================================");
			System.out.println("Usamos ahora un archivo JSON representativo del");
			System.out.println("mismo objeto de Producto");	
			System.out.println("===================================================\n");	
			System.out.println("Notas:");
			System.out.println("\t1) El archivo JSON recuperado como recurso se encuentra en:");
			System.out.println("\t   resources/ejemplos_json/producto01.json");
			System.out.println("\t2) Su encoding es 'UTF-8'.");

			String archivoJSONComoRecurso = "/ejemplos_json/producto01.json";
			
			producto = (Producto) jsonb.fromJson(
					ClassLoader.class.getResourceAsStream(archivoJSONComoRecurso), 
					Producto.class);
					
			System.out.println("\n===================================================");
			System.out.println("El ejemplo de Objeto 'Producto' resultante es:");		
			System.out.println("===================================================\n");	
			System.out.println(producto.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
