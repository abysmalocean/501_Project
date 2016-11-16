

interface Parametrization 
{
	public double penaltyP(int x1, int y1, int x2, int y2);
	public double penaltyF(int x, int y);
	public double penaltyB(int x, int y);
		
}

public class ParamaterClass implements Parametrization{

		int hight=-1;
		int width=-1;
		int bg[][]=null;
		int fg[][]=null;
		int img[][]=null;
		int bg255 = 0;
		int fg255 = 0;
		
		int fgHist[]=new int[256];
		int bgHist[]=new int[256];
		
		double fgConst=10;
	    double bgConst=10;
	    double fgHintBump=1000;
	    double bgHintBump=1000;
	    double graphConst=1000;
		double dThreshold=3;
		double distExpConst=100.0;
		
		public void setbg(int bgin[][])
		{
			this.bg = bgin;
		}
		public void setfg(int fgin[][])
		{
			this.fg = fgin;
		}
		public void setimg(int imgin[][])
		{
			this.img = imgin;
		}
		
		public void sethight(int inHight)
		{
			this.hight = inHight;
		}
		
		public void setwidth(int inWidth)
		{
			this.width = inWidth;
		}
		
		public int[][] getbg()
		{
			return this.bg;
		}
		public int[][] getfg()
		{
			return this.fg;
		}
		public int[][] getimg()
		{
			return this.img;
		}


		public void initializeHist()
		// img[0...height-1][0...width-1], same for fg and bg 
		// i.e. first index is Y, second index is X
		{
			int x,y;
			int c;
			
			for (c=0;c<256;c++)
			{
				fgHist[c]=1;bgHist[c]=1; //initialize psedocounts
			}
			
			for (x=0;x<this.width;x++) for (y=0;y<this.hight;y++)
			{
				if (fg[y][x]>0) fgHist[img[y][x]]++;
				if (bg[y][x]>0) bgHist[img[y][x]]++;
				
				if(fg[y][x] == 255) fg255++;
				if(bg[y][x] == 255) bg255++;
			}
			
			
		
		}

		public double penaltyP(int x1, int y1, int x2, int y2)
		{
			int c1=img[y1][x1];
			int c2=img[y2][x2];
			double dist = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
			double cDiff=Math.sqrt((c1-c2)*(c1-c2))/255;
			double penalty=0;
			if (dist>dThreshold)
				penalty=0;
			else if (dist==0) // this shouldn't happen!
				penalty=0;
			else
			{
				penalty = graphConst * (1.0/dist) * (Math.exp(-distExpConst*cDiff));
			}
			return penalty;
		}

		public  double penaltyF(int x, int y)
		{
			int c=img[y][x];
			int isInHint=bg[y][x];
			double bgProb=((double)bgHist[c])/((double)fgHist[c]+(double)bgHist[c]);
			double penalty= bgConst*(bgProb +bgHintBump*isInHint);
			return penalty;
		}

		public double penaltyB(int x, int y)
		{
			int c=img[y][x];
			int isInHint=fg[y][x];
			double fgProb=((double)fgHist[c])/((double)fgHist[c]+(double)bgHist[c]);
			double penalty= fgConst*(fgProb +fgHintBump*isInHint);
			return penalty;
		}

	}
