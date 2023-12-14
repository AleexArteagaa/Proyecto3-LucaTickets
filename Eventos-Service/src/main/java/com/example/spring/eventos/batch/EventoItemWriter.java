package com.example.spring.eventos.batch;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.example.spring.eventos.model.Evento;
import com.example.spring.eventos.model.Recinto;

public class EventoItemWriter implements ItemWriter<Evento>{

    private Connection connection;

    public EventoItemWriter() {


		String jdbcUrl ="jdbc:mysql://monorail.proxy.rlwy.net:31899/lucaticket?";
		String user = "root";
		String password = "bHaaHdacbCGB-GB41g2H-cFDHGbBB63f";
        try {
			this.connection = DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void write(Chunk<? extends Evento> chunk) throws Exception {
        System.out.println("--- Escribiendo Datos");

        for (Evento evento : chunk) {
        	
            Recinto recinto = evento.getRecinto();
            Recinto existingRecinto = findExistingRecinto(recinto);
            
            if (existingRecinto == null) {

            	insertRecinto(recinto);
            }

            Evento existingEvento = findExistingEvento(evento);

            if (existingEvento == null) {
            	
                insertEvento(evento);
            }
        }
    }

    private Evento findExistingEvento(Evento newEvento) throws SQLException {
    	String sql = "SELECT * FROM eventos WHERE nombre = ? AND fecha_evento = ? AND hora_evento = ? AND id_recinto IN (SELECT id_recinto FROM recintos WHERE nombre = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEvento.getNombre());
            statement.setDate(2, java.sql.Date.valueOf(newEvento.getFechaEvento()));
            statement.setTime(3, java.sql.Time.valueOf(newEvento.getHoraEvento()));
            statement.setString(4, newEvento.getRecinto().getNombre());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    
                    return new Evento(
                            resultSet.getLong("id_evento"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion_corta"),
                            resultSet.getString("descripcion_extendida"),
                            resultSet.getString("foto"),
                            resultSet.getDate("fecha_evento").toLocalDate(),
                            resultSet.getTime("hora_evento").toLocalTime(),
                            resultSet.getDouble("precio_minimo"),
                            resultSet.getDouble("precio_maximo"),
                            resultSet.getString("normas"),
                            
                            new Recinto(
                                    resultSet.getLong("id_recinto"),
                                    resultSet.getString("nombre")
                            )
                    );
                }
            }
        }

        return null;
    }

    private void insertEvento(Evento evento) throws SQLException {
    	
        Recinto recinto = evento.getRecinto();
        Long idRecinto = findRecintoIdByNombre(recinto.getNombre());

    	
        String sql = "INSERT INTO eventos (nombre, descripcion_corta, descripcion_extendida, foto, fecha_evento, hora_evento, precio_minimo, precio_maximo, normas, id_recinto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, evento.getNombre());
            statement.setString(2, evento.getDescripcionCorta());
            statement.setString(3, evento.getDescripcionExtendida());
            statement.setString(4, evento.getFoto());
            statement.setDate(5, java.sql.Date.valueOf(evento.getFechaEvento()));
            statement.setTime(6, java.sql.Time.valueOf(evento.getHoraEvento()));
            statement.setDouble(7, evento.getPrecioMinimo());
            statement.setDouble(8, evento.getPrecioMaximo());
            statement.setString(9, evento.getNormas());
            statement.setLong(10, idRecinto);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        evento.setIdEvento(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }
    
    
    private Long findRecintoIdByNombre(String nombreRecinto) throws SQLException {
        String sql = "SELECT id_recinto FROM recintos WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreRecinto);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id_recinto");
                }
            }
        }

 
        throw new SQLException("Recinto no encontrado por nombre: " + nombreRecinto);
    }

    private Recinto findExistingRecinto(Recinto recinto) throws SQLException {
        String sql = "SELECT * FROM recintos WHERE nombre LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recinto.getNombre());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Recinto(
                            resultSet.getLong("id_recinto"),
                            resultSet.getString("nombre"),
                            resultSet.getString("ciudad"),
                            resultSet.getString("direccion"),
                            resultSet.getString("tipo_recinto"),
                            resultSet.getInt("aforo")
                    );
                }
            }
        }

        
        return null;
    }

    private void insertRecinto(Recinto recinto) throws SQLException {
        String sql = "INSERT INTO recintos (nombre, ciudad, direccion, tipo_recinto, aforo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, recinto.getNombre());
            statement.setString(2, recinto.getCiudad());
            statement.setString(3, recinto.getDireccion());
            statement.setString(4, recinto.getTipoRecinto());
            statement.setInt(5, recinto.getAforo());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        recinto.setIdRecinto(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

	

}
