import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	
	private HashSet<Vertex> VertexRoster = new HashSet<Vertex>();
	private ParamaterClass Patamater = null;
	private double[][] graphFlowMatrix = null;
	private int hight;
	private int width;
	private int totalV;
	
	int[] distance;
	int[] parents ;
	int[] visitHistory; 
	
	private Vertex Source;
	private Vertex Target;
	
	//FlowNetWork
	public double[][]  Flow;
	public double[][]  Resudual;
	
	private LinkedList<Integer> path = new LinkedList<Integer>();
	private LinkedList<Integer> SourceRechable = new LinkedList<Integer>();
	
	public Graph(HashSet<Vertex> InVertexRoster,ParamaterClass InPatamater) throws IOException
	{
		this.VertexRoster = InVertexRoster;
		this.Patamater = InPatamater;
		this.hight = Patamater.hight;
		this.width = Patamater.width;
		this.totalV = this.hight * this.width;
		// init the flow and residual network
		initFlowResidualnetWork();
		if(graphFlowMatrix == null)
		{
			graphFlowMatrix = new double[this.hight][this.width];
		}
		BuildSource();
		Buildtarget();
		
		FordFulkerson();
		
		//printPath(BFSGetPath());
		
	}
	
	public void initFlowResidualnetWork()
	{
		this.Flow = new double[this.totalV+2][this.totalV+2];
		this.Resudual = new double[this.totalV+2][this.totalV+2];
		for(int i = 0; i < this.totalV+2; i++)
			for(int j = 0; j < this.totalV+2; j ++)
			{
				this.Flow[i][j]     = 0 ;
				this.Resudual[i][j] = 0;
			}
		
	}
	
	public Vertex BuildSource()
	{ 
		this.Source = new Vertex(this.Patamater);
		this.Source.SetID(-1);
		this.Source.SetXY(-1,-1);
	    for(int i = 0; i < this.Patamater.hight;i ++)
	    {	
	    	for(int j = 0; j < this.Patamater.width; j ++)
	    	{
	    		double temp = ProjectHelper.VertexSearch(i,j,this.Patamater,this.VertexRoster).getPB();
	    		this.Source.setPijSource(i, j, temp);
	    	}
		}
	    this.VertexRoster.add(this.Source);
	    System.out.println("Source addjencent list is " + this.Source.addjlist.size());
		return this.Source;
		
	}
	
	public Vertex Buildtarget()
	{
		this.Target = new Vertex(this.Patamater);
		this.Target.SetID(-2);
		this.Target.SetXY(-1,-1);
	    for(int i = 0; i < this.Patamater.hight;i ++)
	    {	
	    	for(int j = 0; j < this.Patamater.width; j ++)
	    	{
	    		Vertex tempvertex = ProjectHelper.VertexSearch(i,j,this.Patamater,this.VertexRoster);
	    		tempvertex.addtargetToAddjList(this.Target);
	    		double temp = tempvertex.getPF();
	    		this.Target.setPijSource(i, j, temp);
	    	}
		}
	    this.VertexRoster.add(this.Target);
	    System.out.println("Target addjencent list is " + this.Target.addjlist.size());
	    //System.out.println(this.Patamater.bg255);
	    //System.out.println(this.Patamater.fg255);
		return this.Target;
		
	}
	public void makePath(int s, int v)
	{
		//System.out.println("S ---> "+s);
		//System.out.println("V ---> "+v);
		if(v == s)
		{	
			int temps;
			if(s == this.totalV)
			{
				//source
				temps = -1;
			}else if(s == this.totalV +1)
			{
				//target
				temps = -2;
			}else
			{
				// normal vertex
				temps = s;
			}
			this.path.add(temps);
		}else if(this.parents[v] == -1)
		{
			
			System.out.println("no route");
			
		}else
		{
			makePath(s,this.parents[v]);
			int tempv;
			if(v == this.totalV)
			{
				//source
				tempv = -1;
			}else if(v == this.totalV +1)
			{
				//target
				tempv = -2;
			}else
			{
				// normal vertex
				tempv = v;
			}
			this.path.add(tempv);
		}
	}
	
	public LinkedList<Integer> BFSGetPath()
	{
		// Source id is vertex.id = -1 but the index is totalV +1
		// Target id is vertex.id = -2 but the index is totalV +2
		
		makePath(totalV,totalV+1);
		return path;
		
	}
	
	public void printPath(LinkedList<Integer> inpath)
	{
		System.out.print("Shortest path ----->");
		for(int i = 0; i < inpath.size();i++)
		{
			System.out.print(inpath.get(i)+ " ");
		}
		System.out.print("\n");
	}
	
	public int BFS()
	{
		//+2 add source and target
		// Source id is vertex.id = -1 but the index is totalV
		this.distance = new int[this.totalV+2];
		this.parents  = new int[this.totalV+2];
		this.visitHistory = new int[this.totalV+2];
		// 0 means not visited, 1 means visited before
		for(int i = 0; i < this.totalV+2; i++)
		{
			this.distance[i] = 100000;
			this.parents[i]  = -1;
			this.visitHistory[i] = 0;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(-1);
		Vertex tempVertex = null;
		
		// Source distance is 0
		this.distance[this.totalV] = 0;
		this.visitHistory[this.totalV] = 1;
		
		while(!queue.isEmpty())
		{
			//System.out.print("LiangXU\n");
			tempVertex = null;
			int current = queue.remove();
			//get the vertex in the list
			//System.out.println("Current is " + current);
			if(current == -1)
			{
				// -1 is the Source
				tempVertex = this.Source;
				current = this.totalV ;
				//System.out.print("LiangXU\n");
				//System.out.println("tempVertex.addjlist.size() -- >" + tempVertex.getID());
				
				
			}else if( current == -2 )
			{
				// -2 is the Target
				tempVertex = this.Target;
				current = this.totalV + 1;
				System.out.print("Found Shortest path\n");
				return 0;
			}else
			{
				tempVertex = ProjectHelper.VertexSearch_ById(current,this.Patamater,this.VertexRoster);
				//tempVertex.Print();
				
			}
			//System.out.println("tempVertex.addjlist.size() -- >" + tempVertex.addjlist.size());
			
			for(int i = 0; i < tempVertex.addjlist.size(); i++)
			{
				int tempid = tempVertex.addjlist.get(i).id;
				
				//System.out.println("Tempid is --->" + tempid);
				
				if(tempid == -2)
				{
					// target found
					this.distance[this.totalV+1] = distance[current] + 1;
					this.parents[this.totalV+1] = current;
					//System.out.println("distance is " + this.distance[this.totalV+1]);
					//System.out.println("Target Parent is " + this.parents[this.totalV+1]);
					//System.out.print("Found Shortest path Liang \n");
					return 0;
				}
						
				if(this.distance[tempid]==100000)
				{	
					//System.out.println("");
					//Not visit before
					this.distance[tempid] = distance[current] + 1 ;
					this.parents[tempid]  = current;
					queue.offer(tempid);
				}
			}
		}
		
		return 1;
	}
	
	public void clearpath()
	{
		this.path.clear();
	}
	
	public int FordFulkerson() throws IOException
	{
		Vertex vertex0;
		Vertex vertex1;
		int display_distance = 0;
		
		while(true)
		{
			clearpath();
			double MinCapacity = -1;
			double TempCapacity = -1;
			int MinCapacityVertexV0 = -1;
			int MinCapacityVertexV1 = -1;

			//BFS find the min path
			BFS();
			BFSGetPath();
			//System.out.println("First element is " + this.path.get(0));
			if(this.path.size()<2)
			{
				System.out.println("Search Complete");
				for(int i = 0 ; i < this.totalV ; i++)
				{
					if(this.distance[i]<100000)
					{
						this.SourceRechable.add(i);
					}
				}
				break;
			}
			//printPath(this.path);
			//System.out.println("Distance is " + display_distance);
			// For dispaly indicating the system is working
			if(display_distance < this.distance[this.totalV+1])
			{
				display_distance = this.distance[this.totalV+1];
				System.out.println("Distance is " + display_distance);
			}
			//1. Check Capacity from the source
			//2. Check Capacity to the Target
			//3. get the min Capacity
			//4. update the flow and residual network
			
			
			//1. Capacity from the source
			vertex0 = ProjectHelper.VertexSearch_ById(this.path.get(0), this.Patamater, this.VertexRoster);
			vertex1 = ProjectHelper.VertexSearch_ById(this.path.get(1), this.Patamater, this.VertexRoster);
			MinCapacity = vertex0.getPij(vertex1.getX(),vertex1.getY());
			MinCapacityVertexV0 =-1; 
			MinCapacityVertexV1 = this.path.get(1);
			//2. Capacity to the Target
			vertex0 = ProjectHelper.VertexSearch_ById(this.path.get(this.path.size()-1), this.Patamater, this.VertexRoster);
			vertex1 = ProjectHelper.VertexSearch_ById(this.path.get(this.path.size()-2), this.Patamater, this.VertexRoster);
			TempCapacity = vertex0.getPij(vertex1.getX(),vertex1.getY());
			if(MinCapacity > TempCapacity)
			{
				MinCapacity = TempCapacity;
				MinCapacityVertexV0 = this.path.get(this.path.size()-2); 
				MinCapacityVertexV1 = this.path.get(this.path.size()-1);
			}
			//3.get the min Capacity
			if(this.path.size()>3)
			{
				for(int i = 1;i < this.path.size()-2;i++)
				{
					vertex0 = ProjectHelper.VertexSearch_ById(this.path.get(i), this.Patamater, this.VertexRoster);
					vertex1 = ProjectHelper.VertexSearch_ById(this.path.get(i+1), this.Patamater, this.VertexRoster);
					TempCapacity = vertex0.getPij(vertex1.getX(),vertex1.getY());
					if(MinCapacity > TempCapacity)
					{
						MinCapacity = TempCapacity;
						MinCapacityVertexV0 = this.path.get(i); 
						MinCapacityVertexV1 = this.path.get(i+1);
					}
				}
			}
			//4 update the flow and residual network
			/*
			for(int i = 0; i < this.path.size()-1; i++)
			{
				int v0 = this.path.get(i);
				int v1 = this.path.get(i+1);
				if(v0 == -1)
				{
					v0 = this.totalV;
				}
				if(v1 == -2)
				{
					v1 =this.totalV+1;
				}
				//Flow network
				this.Flow[v0][v1] = this.Flow[v0][v1] - MinCapacity;
				this.Flow[v1][v0] = this.Flow[v1][v0] + MinCapacity;
				//Resisual Network
				this.Resudual[v0][v1] = this.Resudual[v0][v1] + MinCapacity;
				this.Resudual[v1][v0] = this.Resudual[v1][v0] - MinCapacity;
			}
			*/
			// Update the edge in vertex
			// a----->Only works on the image vertex
			for(int i = 1; i < this.path.size()-2; i++)
			{
				
				int v0 = this.path.get(i);
				int v1 = this.path.get(i+1);
				//Flow network
				vertex0 = ProjectHelper.VertexSearch_ById(v0, this.Patamater, this.VertexRoster);
				vertex1 = ProjectHelper.VertexSearch_ById(v1, this.Patamater, this.VertexRoster);
				vertex0.setPij(vertex1.getX(), vertex1.getY(),vertex0.getPij(vertex1.getX(), vertex1.getY()) - MinCapacity);
				vertex1.setPij(vertex0.getX(), vertex0.getY(),vertex1.getPij(vertex0.getX(), vertex0.getY()) + MinCapacity);
				int found = 0;
				for(int temp_search = 0; temp_search< vertex1.addjlist.size();temp_search++)
				{
					adjacentlsit templist = vertex1.addjlist.get(temp_search); 
					if(templist.id == vertex1.getID())
					{
						found = 1;
					}
				}
				if(found == 0)
				{
					// not found the vertex in the adj list, we should add it to it, becasue the flow change
					vertex1.addjlist.add(new adjacentlsit(vertex0.getX(),vertex0.getY(),vertex0.getID()));
				}
				
			}
			//b ------> for source and target
			// source
			vertex0 = ProjectHelper.VertexSearch_ById(this.path.get(0), this.Patamater, this.VertexRoster);
			vertex1 = ProjectHelper.VertexSearch_ById(this.path.get(1), this.Patamater, this.VertexRoster);
			vertex0.setPij(vertex1.getX(),vertex1.getY(),vertex0.getPij(vertex1.getX(),vertex1.getY())-MinCapacity);
			// target
			vertex0 = ProjectHelper.VertexSearch_ById(this.path.get(this.path.size()-1), this.Patamater, this.VertexRoster);
			vertex1 = ProjectHelper.VertexSearch_ById(this.path.get(this.path.size()-2), this.Patamater, this.VertexRoster);
			vertex0.setPij(vertex1.getX(),vertex1.getY(),vertex0.getPij(vertex1.getX(),vertex1.getY())-MinCapacity);
			
			
			
			//5 remove the adjencent list
			//System.out.println("MinCapacityVertexV0 "+MinCapacityVertexV0);
			
			//System.out.println("MinCapacityVertexV1 "+MinCapacityVertexV1);
			
			vertex0 = ProjectHelper.VertexSearch_ById(MinCapacityVertexV0, this.Patamater, this.VertexRoster);
			vertex1 = ProjectHelper.VertexSearch_ById(MinCapacityVertexV1, this.Patamater, this.VertexRoster);
			for(int temp_search = 0;temp_search < vertex0.addjlist.size();  temp_search++)
			{
				adjacentlsit templist = vertex0.addjlist.get(temp_search); 
				if(templist.id == vertex1.getID())
				{
					vertex0.addjlist.remove(templist);
				}
			}
			//System.out.println("vertex get x  " + vertex1.getX());
			//System.out.println("vertex get y  " + vertex1.getY());
			
			//System.out.println("Min Capacity is " + MinCapacity +"\n\n");
		}
		// create image out put
		
		int[][] outputfgimage = new int[this.Patamater.hight][this.Patamater.width];
		int[][] outputbgimage = new int[this.Patamater.hight][this.Patamater.width];
		for(int i = 0 ; i < this.Patamater.hight;i++)
		{
			for(int j = 0 ; j < this.Patamater.width;j++)
			{
				outputfgimage[i][j] = 255;
				outputbgimage[i][j] = 0;
			}
		}
		
		for(int i = 0; i < this.SourceRechable.size();i++)
		{
			Vertex temperV = ProjectHelper.VertexSearch_ById(this.SourceRechable.get(i), this.Patamater, this.VertexRoster);
			int x = temperV.getX();
			int y = temperV.getY();
			outputfgimage[x][y] = 0;
			outputbgimage[x][y] = 255;
		}
		ImageManuplation.CreatImage(ProjectHelper.fgOutImgPath, Patamater, outputbgimage);
		ImageManuplation.CreatImage(ProjectHelper.bgOutImgPath, Patamater, outputfgimage);
		
		return 1;
		
	}

}

































