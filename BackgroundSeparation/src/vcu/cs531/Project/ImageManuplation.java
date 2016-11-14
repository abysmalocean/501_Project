package vcu.cs531.Project;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManuplation {
	
	public static int[][] LoadImageApp(String imagePath, ParamaterClass paramater) {
		BufferedImage image = null;
		try{
		File f = new File(imagePath); 
		image = ImageIO.read(f);
		}catch(IOException e){
			System.out.println("read file error");
			e.printStackTrace();
		}
	int w = image.getWidth(null);
	int h = image.getHeight(null);
	//System.out.println(w);
	//System.out.println(h);
	int[][] array = new int[w][h];
	for (int j = 0; j < w; j++) {
	    for (int k = 0; k < h; k++) {
	    	Color color = new Color(image.getRGB(j, k));
	        float red = (float) (color.getRed() * 0.299);
	        float green = (float) (color.getGreen() * 0.587);
	        float blue = (float) (color.getBlue() *0.114);
	        int sum = (int) (red + green + blue);
	        if(sum > 255)
	        	sum = 255;
	        if(sum < 0)
	        	sum = 0;
	        array[j][k] = sum;
	        if (sum == 255 )
	        {
	        	//System.out.println("[ "+j+" ]"+"[ "+k+" ]"+"-->"+"[ "+array[j][k]+" ]");
	        }
	    }
	}
	return array;
	}
}