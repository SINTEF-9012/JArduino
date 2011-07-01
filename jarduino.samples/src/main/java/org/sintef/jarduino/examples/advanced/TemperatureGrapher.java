package org.sintef.jarduino.examples.advanced;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.comm.Serial4JArduino;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class TemperatureGrapher extends JArduino{

	//Set the delay to fit you needs (60000 = 1 minute)
	private int delay = 1000; 
	//Change this to false to graph out raw sensor reading also
	private boolean onlyDisplayTemperature = true;
	
	private AnalogPin analogPin = AnalogPin.A_0;
	private short sensorValue;
	private long counter; 
	private final int B=3975; 
	private float resistance;
	//JFreeChart components
	private final XYSeries temperatures = new XYSeries("Temperature");;
	private final XYSeries raw = new XYSeries("Raw value");;
	private ApplicationFrame frame;
	private XYDataset dataset;
	
	
	public TemperatureGrapher(String port) {
		super(port);
	}


    @Override
    protected void setup() {
    	//This code creates the frame that the graph are displayed in
    	frame = new ApplicationFrame("Temperature logger");
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        frame.setContentPane(chartPanel);
    	frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    @Override
    protected void loop() {
    	dataset =  new XYSeriesCollection();
        // read the analog in value:
        sensorValue = analogRead(analogPin);

        // print the results to the serial monitor:
        System.out.print("sensor = " + sensorValue);
        //Calculate the temperature
        resistance=(float)(1023-sensorValue)*10000/sensorValue; 
        float temp=(float) (1/(Math.log(resistance/10000)/B+1/298.15)-273.15);
        System.out.println("Temperature: " + temp);
        
        //Adds the temperature to the graph
        temperatures.add(counter, temp);
        ((XYSeriesCollection) dataset).addSeries(temperatures);
        
       //adds the raw value if choosen to
        if(!onlyDisplayTemperature){
        	raw.add(counter, sensorValue);
        	((XYSeriesCollection) dataset).addSeries(raw);
        }
        
        //waits
        delay(delay);
        
        //packs the chart and updates it on screen
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        frame.setContentPane(chartPanel);
    	frame.pack();
    	
    	counter++;
    }



	public static void main(String[] args) {

        String serialPort;
        if (args.length == 1) {
            serialPort = args[0];
        } else {
            serialPort = Serial4JArduino.selectSerialPort();
        }
        
        JArduino arduino = new TemperatureGrapher(serialPort);
        arduino.runArduinoProcess();
    }
    
    
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Temperature History Chart",      // chart title
            "Reading",                      // x axis label
            "Value",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        if(!onlyDisplayTemperature){
        	renderer.setSeriesShapesVisible(1, false);
        }
        plot.setRenderer(renderer);

        return chart;
    }

}
