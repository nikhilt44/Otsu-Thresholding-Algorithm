import processing.core.*;
import java.util.*;

public class otsu_standard extends PApplet
{
	PImage src;
	PImage dest;
	
	public void setup()
	{
		 src = loadImage("final.png");
		 size(src.width,src.height);
		 long total = src.width*src.height;
		 System.out.println(total);
		 src.filter(GRAY);
		 image(src,0,0);
		 Map<Integer,Long> m = new HashMap<Integer,Long>();
		 int i,j;
		 //Getting histogram in hashmap
		 for (i = 0; i < src.width; i++) 
		    {
		      for (j = 0; j < src.height; j++ ) 
		      {
		    	  int loc = i + j*src.width;
		    	  // System.out.println(brightness(src.pixels[loc])); 
		    	  int temp = (int) brightness(src.pixels[loc]);
		    	  if(m.get(temp) == null)
		    	  {
		    		 m.put(temp,(long)1); 
		    	  }
		    	  else
		    	  {
		    		  long temp1 = m.get(temp);
		    		  temp1++;
		    		  m.put(temp,temp1);
		    	  }
		      }
		  }
		// System.out.println(m);
		 
		 
		 //Otsu's Algorithm
		 double bet_class_var = 0;
		 double var_max=0;
		 Map<Integer,Long> bg = new HashMap<Integer,Long>();
		 Map<Integer,Long> fg = new HashMap<Integer,Long>();
		 int sol_th=50;
		 
	    for(int th=50;th<215;th++)
		{
		   Set a = m.keySet();
			 
			for(Object temp : a)
			 {
				int temp1 = (int)temp;
				if(temp1<th)
				{
				   bg.put(temp1,m.get(temp1));	
				}
				else
				{
				   fg.put(temp1,m.get(temp1));	
				}
			 }
			 
		  //   System.out.println(bg);
		   //  System.out.println(fg);
			 float[] bg1 = calc_bg(bg,total);
			 float[] fg1 = calc_fg(fg,total);
			 
			// System.out.println(Arrays.toString(bg1));
			 //System.out.println(Arrays.toString(fg1));
			 //first val is weight and second in mean
		 
			 if(th==50)
			 {
		       var_max = bg1[0]*fg1[0]*Math.pow(bg1[1]-fg1[1],2);
			 }
			 else
			 {
				 bet_class_var = bg1[0]*fg1[0]*Math.pow(bg1[1]-fg1[1],2);
			 }
			 
			 if(bet_class_var>var_max)
			 {
				var_max =  bet_class_var;
				 sol_th = th;
			 }
   }
	    
	    System.out.println(var_max);
	    System.out.println(sol_th);
	 }
	
	 private float[] calc_fg(Map<Integer, Long> fg,long total) 
	 {
		// TODO Auto-generated method stub
		int i,temp=0,sum=0;
		float wfg;     //weight
		float mfg;     //mean
		Set a = fg.keySet();
		for(Object b : a)
		{
		   sum += fg.get(b);
		   temp += (int)b*fg.get(b); 
		}
		//System.out.println(sum);
		wfg = (float)sum/total;
	//	System.out.println(wfg);
		mfg = temp/sum;
		float[] w = {wfg,mfg};
		return w;
	}


	private float[] calc_bg(Map<Integer, Long> bg,long total) 
	{
		// TODO Auto-generated method stub
		int i,temp=0,sum=0;
		float wbg;     //weight
		float mbg;     //mean
		Set a = bg.keySet();
		for(Object b : a)
		{
		   sum += bg.get(b);
		   temp += (int)b*bg.get(b); 
		}
		//System.out.println(sum);
		wbg = (float)sum/total;
		//System.out.println(wbg);
		mbg = temp/sum;
		float[] w = {wbg,mbg};
		return w;
		
	}

	public void draw() 
	 {  
	   // image(src,0,0);
    }
}
