package br.com.corleone.medlife.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  private String nome;

  private String telefone;

  @Lob
  private Byte foto;

  @OneToOne
  private Roles role = new Roles();

  public Usuario() {

  }

  public Usuario(String username, String password, String nome, String telefone) {
    this.username = username;
    this.password = password;
    this.nome = nome;
    this.telefone = telefone;
  }

}
