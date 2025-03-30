package br.com.projeto.descarteResiduo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("pontos")
public class Point {

	@Id
	private Long id;
	private String nome;
	private String endereco;
	private String zona;
	private String contatos;
	private String horarioExpediente;
}
