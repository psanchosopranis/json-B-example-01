package es.pssstests.ejemplo.ejecutables;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyNamingStrategy;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import es.pssstests.ejemplo.agregados.Factura;
import es.pssstests.ejemplo.componentes.Direccion;
import es.pssstests.ejemplo.componentes.PersonaFisicaOJuridica;
import es.pssstests.ejemplo.componentes.Producto;
import es.pssstests.ejemplo.componentes.Receptor;

public class Ejemplo04SerializacionConstruccionFactura {

	
	public static void main(String[] args) {

		try {
			
		    // Custom configuration
	        JsonbConfig config = new JsonbConfig()
	            .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_DASHES)
	            .withEncoding("UTF-8")
	            .withBinaryDataStrategy(BinaryDataStrategy.BASE_64)
	            .withDateFormat("dd.MM.yyyy HH:mm:ss", new Locale("es_ES"))
	            .withNullValues(true)
	            .withFormatting(true);

	        Jsonb jsonb = JsonbBuilder.create(config);
			
			Factura factura = construyeEjemploDeFactura();
			
			System.out.println("\n===================================================");
			System.out.println("Un ejemplo de Objeto 'Factura':");
			System.out.println("===================================================\n");
			System.out.println(factura.toString());

			String facturaAsJSONString = jsonb.toJson(factura);
			
			System.out.println("\n===================================================");
			System.out.println("Serializado como String JSON:");		
			System.out.println("===================================================\n");			
			System.out.println(facturaAsJSONString);
			System.out.println("Nota: Puede observarse:");
			System.out.println("\t1) Serialización de elementos con valor 'null' (ver 'direccion')");
			System.out.println("\t   al incluir la opción '.withNullValues(true)'");
			System.out.println("\t2) Serializacion de Fechas con formato 'dd.mm.aaaa hh:mm:ss'");
			System.out.println("\t   al incluir la opción '.withDateFormat(\"dd.MM.yyyy HH:mm:ss\", new Locale(\"es_ES\"))'");						
			System.out.println("\t3) Serialización transparente de tipos complejos como:");
			System.out.println("\t   'enum' para Persona Física o Jurídica");
			System.out.println("\t   'BigDecimal' para Importes");
			System.out.println("\t4) Serialización transparente de Estructuras (y su anidación) como:");
			System.out.println("\t   'Arrays' (lineas de Descripción de Artículos)");
			System.out.println("\t   'Listas' (Productos, Detalles de Importes, ...)");
			System.out.println("\t5) JSON-B serializa objetos binarios (Ejemplo imágenes) en Base64");
			System.out.println("\t   al incluir la opción '.withBinaryDataStrategy(BinaryDataStrategy.BASE_64)'");			
			System.out.println("\t6) JSON-B ha optado por serializar los atributos por orden alfabético de su nombre");
			System.out.println("\t7) Nótese el uso de PropertyNamingStrategy.LOWER_CASE_WITH_DASHES");

			
//			System.out.println(ReflectionToStringBuilder.toString(Locale.getAvailableLocales(), ToStringStyle.MULTI_LINE_STYLE));
			
			System.out.println("\n===================================================");
			System.out.println("Usamos ahora un archivo JSON representativo del");
			System.out.println("mismo objeto de Factura");	
			System.out.println("===================================================\n");	
			System.out.println("Notas:");
			System.out.println("\t1) El archivo JSON recuperado como recurso se encuentra en:");
			System.out.println("\t   resources/ejemplos_json/factura01.json");
			System.out.println("\t2) Su encoding es 'UTF-8'.");

			String archivoJSONComoRecurso = "/ejemplos_json/factura01.json";
			
			factura = (Factura) jsonb.fromJson(
					ClassLoader.class.getResourceAsStream(archivoJSONComoRecurso), 
					Factura.class);
					
			System.out.println("\n===================================================");
			System.out.println("El ejemplo de Objeto 'Factura' resultante es:");		
			System.out.println("===================================================\n");	
			System.out.println(factura.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	private static Factura construyeEjemploDeFactura() {
		
		Direccion direccion = new Direccion();
		direccion.setLinea1("Martínez Villergas, 52. Edificio B - Planta 6");
		direccion.setLocalidad("MADRID");
		direccion.setCodigoPostal("28027");
		direccion.setProvincia("MADRID");
		direccion.setTelefono1("+34 91 556 0013");
		
		Receptor receptorFactura = new Receptor(
				"Juan Hernández Pérez-Tuñón", 
				PersonaFisicaOJuridica.F, 
				"22222222X", 
				direccion);
				
		Receptor receptorMercancia = new Receptor(
				"Recepcionista", 
				PersonaFisicaOJuridica.F, 
				"N/A", 
				direccion);
		
		List<Producto> productos = new ArrayList<Producto>();
		productos.add(
			new Producto(
					"dg-161034_640", 
					"Vaso de Agua boca ancha terminación recta", 
					6, 
					"PZA", 
					new BigDecimal("1.24"), 
					new BigDecimal("16.00"), 
					"/imagenes/VasoDeAgua.png",
					new String[] {
							"Vaso de cristal transparente de alta resistencia a golpes y cambios bruscos de temperatura.",
							"Válido para lavavajillas.",
							"Disponible en color transparente y azul translúcido." 
					}
				));
		
		productos.add(
				new Producto(
						"vv-161134_820", 
						"Vaso de Vino boca ancha copa media terminación abombada", 
						6, 
						"PZA", 
						new BigDecimal("2.30"), 
						new BigDecimal("16.00"), 
						"/imagenes/CopaDeVino.png",
						new String[] {
								"Elaborado con cristal de Bohemia con resistencia moderada a baja a golpes y cambios bruscos de temperatura.",
								"NO válido para lavavajillas.",
								"Disponible exclusivamente en color transparente.",
								"Alto brillo y elaboración artesanal."
						}
					));
		
		
		productos.add(
				new Producto(
						"BA02-211450_AGE", 
						"Botella de Agua marca EL FONTANAL DE TRUEBA", 
						2, 
						"PZA", 
						new BigDecimal("0.80"), 
						new BigDecimal("8.00"), 
						"/imagenes/BotellaDeAgua.png",
						new String[] {
								"Botella de Agua mineral de mineralizacón baja.",
								"Botella PET baja transferencia ftalatos.",
								"Marca Propia."
						}
					));
		
		String comentarios = 
			   "Comentario de prueba con el primer parrafo de LOREM IPSUM:"
			 + "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
			 + "\nNam fringilla, tortor at varius mattis, lectus nunc ornare nunc, quis pellentesque mi nisi sed est."
			 + "\nMauris ultrices, enim eu aliquet imperdiet, sem mauris eleifend turpis, vel tempus velit nisl quis neque."
			 + "\nDonec placerat malesuada ultrices."
			 + "\nNulla augue ligula, vulputate non nulla in, posuere vulputate urna."
			 + "\nAenean ac lacus eget dui euismod mattis."
			 + "\nMaecenas quis varius odio."
			 + "\nIn aliquet turpis in sodales ultricies.";
		
		Date ahora = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(ahora); 
		c.add(Calendar.DATE, 4);
		Date fechaEntrega = c.getTime();
		
		return new Factura(
				new Integer("01234579"),
				ahora,
				fechaEntrega,
				receptorFactura,
				receptorMercancia,
				productos,
				comentarios
				);
	}
	
}
