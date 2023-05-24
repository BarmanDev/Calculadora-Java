package com.barmandev.layouts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculadora {

	public static void main(String[] args) {
		// Crear una instancia de MarcoCalculadora
		MarcoCalculadora mimarco = new MarcoCalculadora();

		// Configurar la operación de cierre de la ventana al hacer clic en la "X"
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Hacer la ventana visible
		mimarco.setVisible(true);
	}

}

class MarcoCalculadora extends JFrame {

	public MarcoCalculadora() {
		// Configurar el título de la ventana
		setTitle("Calculadora");

		// Configurar la posición y el tamaño de la ventana
		setBounds(500, 300, 450, 300);

		// Crear una instancia de PanelCalculadora
		PanelCalculadora milamina = new PanelCalculadora();

		// Agregar el panel a la ventana
		add(milamina);
	}
}

class PanelCalculadora extends JPanel {

	public PanelCalculadora() {
		comienzo = true;

		// Configurar el diseño del panel principal
		setLayout(new BorderLayout());

		// Crear un botón para mostrar el resultado
		pantalla = new JButton("0");
		pantalla.setEnabled(false);

		// Agregar el botón de pantalla en la parte superior del panel
		add(pantalla, BorderLayout.NORTH);

		// Crear un panel para contener los botones numéricos y de operaciones
		numeracion = new JPanel();
		numeracion.setLayout(new GridLayout(4, 4));

		// Crear instancias de los objetos oyente para los botones
		InsertarNumero insertar = new InsertarNumero();
		Operacion realizarOperacion = new Operacion();

		// Agregar los botones numéricos y de operaciones al panel
		ponerBoton("7", insertar);
		ponerBoton("8", insertar);
		ponerBoton("9", insertar);
		ponerBotonOperaciones("x", realizarOperacion);
		ponerBoton("4", insertar);
		ponerBoton("5", insertar);
		ponerBoton("6", insertar);
		ponerBotonOperaciones("-", realizarOperacion);
		ponerBoton("1", insertar);
		ponerBoton("2", insertar);
		ponerBoton("3", insertar);
		ponerBotonOperaciones("+", realizarOperacion);
		ponerBoton("0", insertar);
		ponerBoton(",", insertar);
		ponerBotonOperaciones("=", realizarOperacion);
		ponerBotonOperaciones("/", realizarOperacion);

		// Agregar el panel de botones al centro del panel principal
		add(numeracion, BorderLayout.CENTER);
	}

	// Método para agregar un botón numérico al panel
	private void ponerBoton(String textoBoton, InsertarNumero oyente) {
		JButton boton = new JButton(textoBoton);
		boton.addActionListener(oyente);
		numeracion.add(boton);
	}

	// Método para agregar un botón de operación al panel
	private void ponerBotonOperaciones(String textoBoton, Operacion oyente) {
		JButton boton = new JButton(textoBoton);
		boton.addActionListener(oyente);
		numeracion.add(boton);
	}

	private class InsertarNumero implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String entrada = e.getActionCommand();

			if (comienzo) {
				// Si es el primer dógito después de una operación, reemplazar el texto en la pantalla
				pantalla.setText(entrada);
				comienzo = false;
			} else {
				// Si no es el primer dígito, agregarlo al texto existente en la pantalla
				pantalla.setText(pantalla.getText() + entrada);
			}

			// Obtener el valor numérico actual de la pantalla
			ultimoValor = Double.parseDouble(pantalla.getText());
		}
	}

	private class Operacion implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String operacionPulsada = e.getActionCommand();

			if (operacionPulsada.equals("+")) {
				// Realizar la operación de suma
				resultado += ultimoValor;
				pantalla.setText("" + resultado);
				ultimaOperacion = "+";
			} else if (operacionPulsada.equals("-")) {
				// Realizar la operación de resta
				double valor = Double.parseDouble(pantalla.getText());
				if (contador == 0)
					resultado = valor;
				else {
					resultado -= ultimoValor;
				}
				pantalla.setText("" + resultado);
				ultimaOperacion = "-";
				contador++;
			} else if (operacionPulsada.equals("x")) {
				// Realizar la operación de multiplicación
				double valor = Double.parseDouble(pantalla.getText());
				if (contador == 0)
					resultado = valor * 1;
				else {
					resultado *= ultimoValor;
				}
				pantalla.setText("" + resultado);
				ultimaOperacion = "x";
				contador++;
			} else if (operacionPulsada.equals("/")) {
				// Realizar la operación de división
				double valor = Double.parseDouble(pantalla.getText());
				if (contador == 0)
					resultado = valor;
				else {
					resultado /= ultimoValor;
				}
				pantalla.setText("" + resultado);
				ultimaOperacion = "/";
				contador++;
			} else {
				// Realizar la última operación seleccionada
				if (ultimaOperacion.equals("+"))
					resultado += ultimoValor;
				if (ultimaOperacion.equals("-"))
					resultado -= ultimoValor;
				contador = 0;
				if (ultimaOperacion.equals("x"))
					resultado *= ultimoValor;
				contador = 0;
				if (ultimaOperacion.equals("/"))
					resultado /= ultimoValor;
				contador = 0;

				pantalla.setText("" + resultado);

				ultimoValor = 0;
			}

			comienzo = true;
		}

		private String ultimaOperacion;
		private int contador;
	}

	private JPanel numeracion;
	private JButton pantalla;
	private boolean comienzo;
	private String ultimaOperacion = "";
	private double resultado;
	private double ultimoValor;
}
