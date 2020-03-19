package br.edu.ifms.ev3.exemplos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafico extends JFrame {
  private static final long serialVersionUID = 6294689542092367723L;
  
  public Grafico(String title,File file) {
    super(title);
    

    // Create dataset
    XYDataset dataset = createDataset(file);
	
   
    // Create chart
    JFreeChart chart = ChartFactory.createXYLineChart(
        "Gráfico de erro por tempo",
        "Tempo",
        "Erro",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false);
   // XYPlot plot = (XYPlot) chart.getPlot();
    
    //plot.getRangeAxis().setInverted(true);
    try {
		ChartUtilities.saveChartAsJPEG(new java.io.File("c:\\Users\\armando\\Desktop\\OBR Java\\PD1_1502.jpg"), chart, 900, 450);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
    
  }

  private XYDataset createDataset(File file) {
	 
	 FileReader fw;
	 BufferedReader br;
	 String line;
	 float erro, x = 0f;
		
    XYSeriesCollection dataset = new XYSeriesCollection();

    XYSeries series = new XYSeries("erro", false, true);
   
    try {
    	fw = new FileReader(file);
    	br = new BufferedReader(fw);
    	
		while ((line = br.readLine())!=null) {
			erro = Float.parseFloat(line);
			x = x + 0.1f;
			series.add(x, erro);
			
			
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    

    //Add series to dataset
    dataset.addSeries(series);
    
    return dataset;
  }

  public static void main(String[] args) {
	   
	final File file,filed, filep,filec;
	
	  
	    filep = new File("C:\\Users\\armando\\Desktop\\OBR Java\\proportional.txt");
		file = new File("C:\\Users\\armando\\Desktop\\OBR Java\\erro.txt");
		filec = new File("C:\\Users\\armando\\Desktop\\OBR Java\\controle.txt");
		filed = new File("C:\\Users\\armando\\Desktop\\OBR Java\\derivative.txt");
    SwingUtilities.invokeLater(new Runnable() {
		
    	@Override
		
		public void run() {
		  Grafico example = new Grafico("Gráfico de erro por tempo",file);
		  example.setSize(800, 400);
		  example.setLocationRelativeTo(null);
		  example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  example.setVisible(true);
		  
		  Grafico example2 = new Grafico("Gráfico de controle por tempo",filec);
		  example2.setSize(800, 400);
		  example2.setLocationRelativeTo(null);
		  example2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  example2.setVisible(true);
		  
		  Grafico example3 = new Grafico("Gráfico de proporcional por tempo",filep);
		  example3.setSize(800, 400);
		  example3.setLocationRelativeTo(null);
		  example3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  example3.setVisible(true);
		  
		  Grafico example4 = new Grafico("Gráfico de derivativa por tempo",filed);
		  example4.setSize(800, 400);
		  example4.setLocationRelativeTo(null);
		  example4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  example4.setVisible(true);
		}
	});
  }
}