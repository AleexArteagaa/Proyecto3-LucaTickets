package com.example.spring.pago.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_eventos")
public class UsuarioEvento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compra")
	private Long idCompra;
	
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Long usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Long evento;


    public UsuarioEvento() {}

    public UsuarioEvento(Long usuario, Long evento) {
        this.usuario = usuario;
        this.evento = evento;
    }
    
    

	public UsuarioEvento(Long idCompra, Long usuario, Long evento) {
		super();
		this.idCompra = idCompra;
		this.usuario = usuario;
		this.evento = evento;
	}

	
	
	public Long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
	}

   
}
