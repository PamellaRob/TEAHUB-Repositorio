/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teahub;

import conexao.ConexaoBanco;
import java.sql.Connection;

public class teste {
    public static void main(String[] args) {
        try {
            Connection conn = ConexaoBanco.conectar();
            System.out.println("✅ Conexão bem-sucedida!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }
}
