package vcu.cs531.Project;

public class Vertex {
	
	private int x   = -1;
	private int y   = -1;
	private int id  = -1;
	private double PF  =  0;
	private double PB  =  0;
	private double[][] Pij ;
	
	private int hight;
	private int width;
	
	public Vertex(int xIn, int yIn, ParamaterClass Patamater) {
	    this.x = xIn;
	    this.y = yIn;
	    this.id = (int) (xIn*Patamater.width + yIn);
	    
	    if(Patamater.fg[xIn][yIn] == 255)
	    {
	    	this.PF = 10000;
	    	this.PB = 0;
	    }else
	    {
	    	this.PF = Patamater.penaltyF(yIn, xIn);
	    }
	    if(this.PB != 0)
	    {
	    	if(Patamater.bg[xIn][yIn] == 255)
	    	{
	    		this.PB = 10000;
	    		this.PF = 0;
	    	}else
	    	{
	    		this.PB = Patamater.penaltyB(yIn, xIn);
	    	}
	    }
	    this.Pij = new double[Patamater.hight][Patamater.width];
	    this.hight = Patamater.hight;
	    this.width = Patamater.width;
	    
	    for(int j = 0; j < Patamater.hight;j ++)
	    	for(int i = 0; i < Patamater.width; i ++)
	    	{
	    		this.Pij[j][i] = Patamater.penaltyP(this.y, this.x, i, j);
	    	}
	  }
	
	public int getID()
	{
		return this.id;
	}
	
	public void Print()
	{
		System.out.println("x is ---> "+this.x);
		System.out.println("y is ---> "+this.y);
		System.out.println("id is ---> "+this.id);
		System.out.println("PF is ---> "+this.PF);
		System.out.println("PB is ---> "+this.PB);
		for(int j = 0; j < this.hight;j ++)
		{
	    	for(int i = 0; i < this.width; i ++)
	    	{
	    		System.out.print( " " + this.Pij[j][i] + " ");
	    	}
		System.out.println("\n");
		}
		
	}

}
