
import java.util.LinkedList;

public class Vertex {
	
	private int x   = -1;
	private int y   = -1;
	private int id  = -1;
	private double PF  =  0;
	private double PB  =  0;
	private double[][] Pij ;
	private static double addjthreadhole = 0.0000001;
	public LinkedList<adjacentlsit> addjlist = new LinkedList<adjacentlsit>();
	private boolean flag = false;
	
	private int hight;
	private int width;
	
	public Vertex(ParamaterClass Patamater)
	{
	    this.hight = Patamater.hight;
	    this.width = Patamater.width;
	    this.Pij = new double[Patamater.hight][Patamater.width];
	}
	public double setPijSource(int i, int j, double value)
	{
		this.Pij[i][j] = value;
		//System.out.println(value);
		if(value > addjthreadhole)
		{	
			addjlist.add(new adjacentlsit(i,j,i*this.width+j));
		}
		return this.Pij[i][j];
	}
	
	public LinkedList<adjacentlsit> addtoAddjList(adjacentlsit Inadjacentlsit, double Penalty)
	{
		if(Penalty > addjthreadhole)
		{	
			addjlist.add(Inadjacentlsit);
		}
		return this.addjlist;
		
	}
	
	public double getPij(int i, int j)
	{
		return this.Pij[i][j];
	}
	public double setPij(int i, int j, double value)
	{
		this.Pij[i][j] = value;
		return this.Pij[i][j];
	}
	
	public double getPF()
	{
		return this.PF;
	}
	
	public double getPB()
	{
		return this.PB;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public Vertex(int xIn, int yIn, ParamaterClass Patamater) {
	    this.x = xIn;
	    this.y = yIn;
	    this.id = (int) (xIn*Patamater.width + yIn);
	    
	    if(Patamater.fg[xIn][yIn] == 255)
	    {
	    	//System.out.println("Liang Xu");
	    	this.PF = 100000;
	    	this.PB = 0;
	    	this.flag = true;
	    }else
	    {	this.flag = false;
	    	this.PF = Patamater.penaltyF(yIn, xIn);
	    }
	    if(!this.flag)
	    {
	    	if(Patamater.bg[xIn][yIn] == 255)
	    	{
	    		//System.out.println("Xu Liang ");
	    		this.PB = 100000;
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
	    {
	    	for(int i = 0; i < Patamater.width; i ++)
	    	{
	    		this.Pij[j][i] = Patamater.penaltyP(this.y, this.x, i, j);
	    		if(this.Pij[j][i] > addjthreadhole)
	    		{
	    			// addj list threas hold
	    			addjlist.add(new adjacentlsit(j,i,j*width+i));
	    		}
	    	}
	    }
	  }
	
	public int getID()
	{
		return this.id;
	}
	
	public int SetID( int ID)
	{
		this.id = ID;
		return this.id;
	}
	
	public void Print()
	{
		System.out.println("x  is ---> "+this.x);
		System.out.println("y  is ---> "+this.y);
		System.out.println("id is ---> "+this.id);
		System.out.println("PF is ---> "+this.PF);
		System.out.println("PB is ---> "+this.PB);
		for(int j = 0; j < this.hight;j ++)
		{
	    	for(int i = 0; i < this.width; i ++)
	    	{
	    		System.out.format("%8.3f", this.Pij[j][i]);
	    		//System.out.print( " " + this.Pij[j][i] + " ");
	    	}
		System.out.println("\n");
		}
		System.out.println("_________adjacentlsit_________");
		for(int i= 0; i < this.addjlist.size();i++)
		{
			System.out.print(this.addjlist.get(i).id + " ");
		}
		
		
	}
	public LinkedList<adjacentlsit> addtargetToAddjList(Vertex target) {
		// TODO Auto-generated method stub
		if(this.PF > addjthreadhole)
		{	
			addjlist.add(new adjacentlsit(-2,-2,-2));
		}
		return this.addjlist;
		
	}
	public void SetXY(int inx, int iny) {
		this.x = inx;
		this.y = iny;
	}
		
	

}
