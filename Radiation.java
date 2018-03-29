import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Radiation {
	public static class Chamber {
		
		int id;
		boolean isExit=false;
		LinkedList<Chamber> neighbours=new LinkedList<>();
		LinkedList<Corridor> edges = new LinkedList<>();
		
		Chamber(int id){
			this.id=id;
		}
		
		void setExit() {
			this.isExit=true;
		}
		
		public String display()
		{
			if(isExit)
				return "Node "+this.id+" is an exit";
				else
					return "Node "+this.id+" is not an exit";
		}
	}
	public static class Corridor {
		
		Chamber A, B; //node 1, node 2
		int T, D, S;  //time, default radiation, radiation speed
		
		Corridor(Chamber a, Chamber b, int t, int d, int s) {
			this.A=a;
			this.B=b;
			this.T=t;
			this.D=d;
			this.S=s;
		}
		
		public int calcRadiation(int totalTime)	{
			return D+(totalTime+T)*S;
		}	
		
		public Chamber getSourceNode() {
			return this.A;
		}
		
		public Chamber getDestinationNode() {
			return this.B;
		}
		
		public Corridor getCorridor(Chamber a, Chamber b)
		{
			if(this.A.equals(a) && this.B.equals(b))
				return this;
			return null;
		}
		public String display()
		{
			return "Node " + this.A.id + " is connected with node " + this.B.id + ". Corridor has T="+this.T+" D="+this.D+" S="+this.S;
		}
	}
        
        public void calculate(Chamber source){
		distance[source]=0;
                pq.add(source);
                while(!pq.isEmpty()){
                    node=pq.poll();
                    if(visited[node]==false)
                        for(int i=0;i<l[node].size();i++){
                            int j=l[node].get(i);
                            if(distance[node]+cost[node][j]<distance[j]){
                                distance[j]=distance[node]+cost[node][j];
                                pq.add(j);
                            }
                        }
                    visited[node]=true;
        }
	}
	
	public static void main(String[] args) throws IOException {
		FileReader fr=new FileReader("radiation0.in");
		Scanner sc=new Scanner(fr);
		BufferedWriter fw = null;
		fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("radiation.out")));
		
		int N=sc.nextInt();
		int M=sc.nextInt();
		int R=sc.nextInt();
		int S=sc.nextInt();
		int E=sc.nextInt();
		int[] distances = new int[N+1];	
		Corridor[] corridors = new Corridor[M+1];
		Chamber[] chambers = new Chamber[N+1];

		
		//
		// POPULATING LISTS...
		//
		for(int i=1;i<=N;i++) {
			chambers[i]=new Chamber(i);
			distances[i]=Integer.MAX_VALUE;
		}
		
		for(int i=1;i<=M;i++) {	
			int id1=sc.nextInt();
			int id2=sc.nextInt();
			corridors[i]=new Corridor(chambers[id1], chambers[id2], sc.nextInt(), sc.nextInt(), sc.nextInt());
			chambers[id1].neighbours.add(chambers[id2]);
			chambers[id2].neighbours.add(chambers[id1]);
			chambers[id1].edges.add(corridors[i]);
			chambers[id2].edges.add(corridors[i]);
		}
		
		for(int i=1;i<=E;i++) {
			chambers[sc.nextInt()].setExit();
		}
		//
		// DONE POPULATING LISTS
		//
		
		
		//
		// PRINTING
		//
		for (int i = 1; i <= N; i++) {
            System.out.println(chambers[i].display());
        }
		for (int i = 1; i <= M; i++) {
            System.out.println(corridors[i].display());
        }
		
		System.out.println("Start node is " + chambers[S].id);
		System.out.println("Maximum radiation is " + R);
		//
		// NOT PRINTING ANYMORE
		//
	
		sc.close();
		fw.close();
	}
}
