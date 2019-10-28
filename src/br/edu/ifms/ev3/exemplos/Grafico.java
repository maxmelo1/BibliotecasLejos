package br.edu.ifms.ev3.exemplos;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Grafico extends JFrame {
	public Grafico() {
		super( "Meu Primeiro Grafico" ); //Define o titulo da tela
	    //add grafico
		//Cria um dataSet para inserir os dados que serão passados para a criação do gráfico tipo Pie
		
		 
		//Adiciona os dados ao dataSet deve somar um total de 100%
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		jf.setSize( 500, 500 );

		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		ds.addValue(0, "a", "1");
		ds.addValue( 2.3, "a", "5" );


		JFreeChart chart = ChartFactory.createLineChart(
		"Teste",
		"Categorias",
		"Valores", ds,
		PlotOrientation.HORIZONTAL, true, true, true );

		CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint( 0, new Color( 0, 255, 0 ) );
		renderer.setSeriesPaint( 1, Color.YELLOW );
		renderer.setSeriesPaint( 2, new Color( 0, 100, 155 ) );

		ChartPanel panel = new ChartPanel( chart );
		jf.add( panel, BorderLayout.CENTER );
		jf.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
      new Grafico().setVisible( true );
	}
}
