/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Clodoaldo Brasilino wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * Clodoaldo Brasilino escreveu este arquivo. Enquanto você puder ver esse 
 * aviso você pode fazer o que quiser com isso. Se nos encontrarmos algum dia,
 * e você achar isso valeu a pena, você pode me pagar uma cerveja em retorno.
 * ----------------------------------------------------------------------------
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author clodbrasilino
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Carregando o Driver do MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Conectando ao Banco
        Connection conexaoAoBanco = 
                DriverManager
                        .getConnection("jdbc:mysql://localhost/ada",
                                "ada","ada");
        
        // Criando consulta
        Statement consultaAoBanco = 
                conexaoAoBanco.createStatement();
        
        // Obtendo resultado da consulta
        ResultSet doBanco = 
                consultaAoBanco.executeQuery("SELECT * FROM professor");
        while(doBanco.next()){
            Professor p = new Professor();
            p.id = doBanco.getInt("id");
            p.matricula = doBanco.getString("matricula");
            p.nome = doBanco.getString("nome");
            p.email = doBanco.getString("email");
            p.deletado = doBanco.getBoolean("deletado");
            System.out.println(p);
        }
        
        Professor aSerInseridoNoBanco = new Professor();
        aSerInseridoNoBanco.matricula = "7654321";
        aSerInseridoNoBanco.nome = "Luzia";
        aSerInseridoNoBanco.email = "luziasantos174@outlook.com";
        aSerInseridoNoBanco.deletado = false;
        
        // Criando outra consulta
        PreparedStatement inserindoNoBanco =
                conexaoAoBanco.prepareStatement("INSERT INTO professor(matricula,nome,email,deletado) values (?,?,?,?)");
        inserindoNoBanco.setString(1, aSerInseridoNoBanco.matricula);
        inserindoNoBanco.setString(2, aSerInseridoNoBanco.nome);
        inserindoNoBanco.setString(3, aSerInseridoNoBanco.email);
        inserindoNoBanco.setBoolean(4, aSerInseridoNoBanco.deletado);
        
        Integer numeroDeLinhasAfetadas = inserindoNoBanco.executeUpdate();
        
        System.out.println("Linhas afetadas: "+numeroDeLinhasAfetadas);
                
    }
}

class Professor {
    Integer id;
    String matricula;
    String nome;
    String email;
    Boolean deletado;
    
    public String toString(){
        return "Professor: ["
                + id + "," 
                + matricula + ","
                + nome + ","
                + email + ","
                + deletado + "]";
                
    }
}