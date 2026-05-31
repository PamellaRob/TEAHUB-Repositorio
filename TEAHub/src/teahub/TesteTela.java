/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teahub;

import teahub.CadastroPacienteView;
import javax.swing.*;

public class TesteTela {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Teste - Pacientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.add(new CadastroPacienteView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}