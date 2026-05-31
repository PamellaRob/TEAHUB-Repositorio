/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    // ⚠️ Altere os dados abaixo para os do seu PostgreSQL
    private static final String URL    = "jdbc:postgresql://localhost:5432/teahub";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "root";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
