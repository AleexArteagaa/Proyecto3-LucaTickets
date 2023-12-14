package com.example.spring.eventos.batch;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.springframework.batch.item.ItemProcessor;


import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;

public class EventoItemProcessor implements ItemProcessor<String[], Evento>{

	@Override
    public Evento process(String[] item) throws Exception {
        System.out.println("--- Procesando datos");

        if (item != null && item.length >= 10) {

            String fecha = item[1];
	        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");    
	        LocalDate localDate = LocalDate.parse(fecha, formatterDate);
	        LocalTime localTime = LocalTime.of(0, 0);
	        if (!item[2].equals(" ")) {
	        	
	        	if (item[2].equalsIgnoreCase("12:30 {M") ) {
	        		localTime = LocalTime.of(12, 30);
				}else if (item[2].equalsIgnoreCase("10:000 AM")|| item[2].equalsIgnoreCase("10:AM") || item[2].equalsIgnoreCase("10 AM")|| item[2].equalsIgnoreCase("10:00A") || item[2].equalsIgnoreCase("10:00A - 2:00P") || item[2].equalsIgnoreCase("10:0 AM")|| item[2].equalsIgnoreCase("10:0 A.M.") || item[2].equalsIgnoreCase("10;00AM")|| item[2].equalsIgnoreCase("1000AM")) {
	        		localTime = LocalTime.of(10, 00);
				}else if (item[2].equalsIgnoreCase("8:000 AM") || item[2].equalsIgnoreCase("8:00 A.M.")|| item[2].equalsIgnoreCase("0800AM")) {
	        		localTime = LocalTime.of(8, 00);
				}else if (item[2].equalsIgnoreCase("NOON") || item[2].equalsIgnoreCase("DAILY") || item[2].equalsIgnoreCase("12:00P") || item[2].equalsIgnoreCase("12 PM") || item[2].equalsIgnoreCase("12:00 P.M.") || item[2].equalsIgnoreCase("4/17/2010")|| item[2].equalsIgnoreCase("12:00 Noon")) {
	        		localTime = LocalTime.of(0, 00);
				}else if (item[2].equalsIgnoreCase("1130")) {
	        		localTime = LocalTime.of(11, 30);
				}else if (item[2].equalsIgnoreCase("9;00 AM")|| item[2].equalsIgnoreCase("9:00 A.M.")) {
	        		localTime = LocalTime.of(9, 0);
				}else if (item[2].equalsIgnoreCase("11 AM") || item[2].equalsIgnoreCase("11:00 A.M.")  || item[2].equalsIgnoreCase("11:0 A.M.") || item[2].equalsIgnoreCase("110:00AM")) {
	        		localTime = LocalTime.of(11, 0);
				}else if (item[2].equalsIgnoreCase("3:00 P.M.")) {
	        		localTime = LocalTime.of(15, 0);
				}else if (item[2].equalsIgnoreCase("2:00 P.M.")) {
	        		localTime = LocalTime.of(14, 0);
				}else if (item[2].equalsIgnoreCase("8:00 P.M.")) {
	        		localTime = LocalTime.of(8, 0);
				}else if (item[2].equalsIgnoreCase("5:00 P.M.")) {
	        		localTime = LocalTime.of(17, 0);
				}else if (item[2].equalsIgnoreCase("6:00P") || item[2].equalsIgnoreCase("6:00 P.M.")) {
	        		localTime = LocalTime.of(18, 0);
				}else if (item[2].equalsIgnoreCase("1:00 P.M.")) {
	        		localTime = LocalTime.of(13, 0);
				}else if (item[2].equalsIgnoreCase("3:30 P.M.")) {
	        		localTime = LocalTime.of(15, 30);
				}
				else {
					
					   	
	        	String hora = item[2].toUpperCase();
	            hora = hora.replace(".", "");
	            hora = hora.replaceFirst("(\\d{1,2}:\\d{2})([APMapm]{2})", "$1 $2");
	            

	            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
	            DateTimeFormatter formatter24Hour = DateTimeFormatter.ofPattern("H:mm");

	            try {
	                localTime = LocalTime.parse(hora, formatterTime);
	            } catch (DateTimeParseException e) {
	                localTime = LocalTime.parse(hora, formatter24Hour);
	            }
	            
				}
			}
	        
	        
            String eventName = item[4];
            String eventShortName = item[5];
            String eventDescription = item[6];
            String foto = "https://www.visittheusa.mx/sites/default/files/styles/hero_l/public/images/hero_media_image/2017-05/9dbea438bcd305129064e4a048cc6b52.jpeg?h=84167f15&itok=Pbyrka1l";
            String normas = "Normas del Evento:\n\n" +
                    "1. **Puntualidad:** Por respeto a todos los asistentes, te pedimos que llegues a tiempo. El evento comenzará puntualmente según el horario establecido.\n\n" +
                    "2. **Prohibiciones:** Queda estrictamente prohibido fumar dentro de las instalaciones del recinto. Asimismo, se prohíbe el ingreso de alimentos y bebidas externas.\n\n" +
                    "3. **Fotografía y Grabación:** La captura de imágenes y grabaciones está permitida, pero te solicitamos ser respetuoso/a con la privacidad de los demás asistentes. No se permite el uso de flashes durante el evento.\n\n" +
                    "4. **Cuidado del Entorno:** Ayúdanos a mantener limpio el lugar. Utiliza los contenedores de basura proporcionados y sigue las indicaciones del personal para el reciclaje.\n\n" +
                    "5. **Interacción:** ¡Diviértete y disfruta del evento! Anima a los artistas de manera positiva y respeta las normas de convivencia. Cualquier comportamiento inapropiado será motivo de expulsión.\n\n" +
                    "Gracias por tu cooperación y esperamos que tengas una experiencia inolvidable en nuestro evento.";
            String precio = item[8];
            Double precioProcesado = 19.36;
            if (precio.toLowerCase().contains("free")) {
                precioProcesado = 0.0;
            } else {
                String regex = "\\$?([0-9]+(\\.[0-9]+)?)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(precio);

                if (matcher.find()) {
                    String numeroEncontrado = matcher.group(1);
                    precioProcesado = Double.parseDouble(numeroEncontrado);
                }
            }
            

            
            Recinto recinto = new Recinto(item[7], "Nueva York", null, null, 0);
            
            return new Evento(eventName, eventShortName, eventDescription, foto, localDate, localTime, precioProcesado, precioProcesado, normas, recinto);
        } else {
            System.out.println("Error: Array no tiene suficientes elementos.");
            return null; 
        }
    }

}
