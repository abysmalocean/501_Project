

import java.util.HashSet;
import java.util.LinkedList;

public class ProjectHelper {
	private static String file_path = "";
	public static String inputImgPath = "";
	public static String fgInImgPath = "";
	public static String bgInImgPath = "";
	public static String fgOutImgPath = "";
	public static String bgOutImgPath = "";
	
	
	public static String setProjectPath(String input_file_path){
		file_path = System.getProperty("user.dir") + "/" + input_file_path;
		//File f = new File(file_path);
		//System.out.println(f.exists());
		return file_path;	
	}

	public static void printProjectPath() {
		System.out.println("Input Image Path is --->" + inputImgPath);
		System.out.println("FgIn  Image Path is --->" + fgInImgPath);
		System.out.println("BgIn  Image Path is --->" + bgInImgPath);
		System.out.println("FgOut Image Path is --->" + fgOutImgPath);
		System.out.println("BgOut Image Path is --->" + bgOutImgPath);
	}

	public static void setAllImgPath() {
		  inputImgPath = ProjectHelper.setProjectPath(inputImgPath);
		  fgInImgPath = ProjectHelper.setProjectPath(fgInImgPath);
		  bgInImgPath = ProjectHelper.setProjectPath(bgInImgPath);
		  fgOutImgPath = ProjectHelper.setProjectPath(fgOutImgPath);
		  bgOutImgPath = ProjectHelper.setProjectPath(bgOutImgPath);
	}

	public static void readProjectImage(ParamaterClass Patamater) {
		Patamater.setimg(ImageManuplation.LoadImageApp(ProjectHelper.inputImgPath,Patamater));
		Patamater.setfg(ImageManuplation.LoadImageApp(ProjectHelper.fgInImgPath,Patamater));
		Patamater.setbg(ImageManuplation.LoadImageApp(ProjectHelper.bgInImgPath,Patamater));
		
	}

	public static boolean checkImgDimension(ParamaterClass Patamater) {
		int temphight = -1;
		int tempwidth = -1;
		if(Patamater.getimg().length == Patamater.getbg().length)
		{
			if(Patamater.getimg().length ==Patamater.getfg().length)
			{
				temphight = Patamater.getimg().length;
				if(Patamater.getimg()[0].length == Patamater.getbg()[0].length)
				{
					if(Patamater.getimg()[0].length == Patamater.getfg()[0].length)
						tempwidth = Patamater.getimg()[0].length ;
						System.out.println("image hight is --->"+temphight);
						System.out.println("image width is --->"+tempwidth);
						Patamater.sethight(temphight);
						Patamater.setwidth(tempwidth);
						System.out.println("Input image dimension Checked    ---->pass");
						return false;
				}
			}
		}
		System.out.println("Input image dimension Checked    ---->NO");
		System.out.println("Input dimension is not same");
		return true;
		
		
		
	}

	public static void initParamater(ParamaterClass Patamater) {
		  ProjectHelper.readProjectImage(Patamater);
		  if(ProjectHelper.checkImgDimension(Patamater))
		  {
			  System.exit(0);
		  }
		  Patamater.initializeHist();
	}

	public static void initpath() {
		  System.out.println("Your Java program should accept 5 command line parameters");
          System.out.println("You shoud have 5 input otherwise the projram using the default");
          System.out.println("using default setting img.png fgIn.png bgIn.png fgOut.png bgOut.png");
          ProjectHelper.inputImgPath = "img.png";
          ProjectHelper.fgInImgPath = "fgIn.png";
          ProjectHelper.bgInImgPath = "bgIn.png";
          ProjectHelper.fgOutImgPath = "fgOut.png";
          ProjectHelper.bgOutImgPath = "bgOut.png"; 
	}

	public static void initpathwithArgs(String[] args) {
		  ProjectHelper.inputImgPath = args[0];
		  ProjectHelper.fgInImgPath = args[1];
		  ProjectHelper.bgInImgPath = args[2];
		  ProjectHelper.fgOutImgPath = args[3];
		  ProjectHelper.bgOutImgPath = args[4];  
	}

	public static LinkedList<Vertex> initVertexs(ParamaterClass Patamater, LinkedList<Vertex> vertexRoster) {
	    //System.out.println("Patamater.width "+ Patamater.width +"Patamater.hight" + Patamater.hight);
		//int counter = -1;
	    for(int j = 0; j < Patamater.hight;j ++)
	    {
	    	for(int i = 0; i < Patamater.width; i ++)
	    	{
	    		//System.out.println("j is "+ j + " and I is "+ i);
	    		Vertex tempvertex = new Vertex( j, i, Patamater);
	    		//tempvertex.Print();
	    		vertexRoster.add(tempvertex);
	    		//counter ++;
	    	}
	    }
	    //System.out.println("System counter is "+ counter);
	    return vertexRoster;
		
	}
	
	public static Vertex VertexSearch(int xIn, int yIn, ParamaterClass Patamater, LinkedList<Vertex> vertexRoster)
	{
		int tempid = Patamater.width * xIn + yIn;
		Vertex V = vertexRoster.get(tempid);
		/*
		for(Vertex V : vertexRoster)
		{
			if (V.getID() == tempid)
			{
				return V;
			}
		}
		*/
		if(V == null)
		{
			System.out.println("Count not find the vertex");
			return null;
		}
		return V;
	}
	
	public static Vertex VertexSearch_ById(int ID, ParamaterClass Patamater, LinkedList<Vertex> vertexRoster)
	{
		int tempid = ID;
		/*
		for(Vertex V : vertexRoster)
		{
			if (V.getID() == tempid)
			{
				return V;
			}
		}
		*/
		if(tempid == -1)
		{
			tempid = Patamater.totalV ;
		}
		if(tempid == -2)
		{
			tempid = Patamater.totalV + 1 ;
		}
		Vertex V = vertexRoster.get(tempid);
		if(V == null)
		{
			System.out.println("Count not find the vertex");
			return null;
		}
		return V;
	}

	public static void printImage(int[][] img) {
		for(int j = 0; j < img.length ; j++)
		{
			for(int i = 0; i <img[0].length; i++ )
			{
				System.out.format("%4d",img[j][i]);
			}
			System.out.println("\n");
		}
	}

	public static boolean CompareImage(String inputImgPath1, String inputImgPath2, ParamaterClass patamater) {
		// TODO Auto-generated method stub
		int[][] temp1 = ImageManuplation.LoadImageApp(inputImgPath1, patamater);
		int[][] temp2 = ImageManuplation.LoadImageApp(inputImgPath2, patamater);
		int h = patamater.width;
		int w = patamater.hight;
		for (int j = 0; j < w; j++) 
		{
		    for (int k = 0; k < h; k++)
		    {
		    	if(temp1[j][k] != temp2[j][k])
		    	{
		    		return false;
		    	}
		    }
		}
		//System.out.println(inputImgPath1 + "\n" + inputImgPath2 + "\n" + "Matrix match each other\n");
		return true;
	}
	
	

	

}
