
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManuplation {
	
	private static int width;
	private static int hight;
	
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
		
		ImageManuplation.width = w;
		ImageManuplation.hight = h;
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
	public static void CreatImage(String outputImagePath, ParamaterClass paramater, int[][] inImg) throws IOException
	{
		BufferedImage imageOut = new BufferedImage(width, hight, BufferedImage.TYPE_BYTE_GRAY);
		 for (int i = 0; i < width; i++)
         {
             for (int j = 0; j < hight; j++)
             {
            	 int rgb = (int)inImg[i][j]<<16 | (int)inImg[i][j] << 8 | (int)inImg[i][j];
                 imageOut.setRGB(i, j, rgb);

             }
         }
		 try
		 {
			 ImageIO.write(imageOut, "PNG", new File(outputImagePath));
		 }catch(IOException e){
				System.out.println("Write file error");
				e.printStackTrace();
			}
	}
	
	
}
