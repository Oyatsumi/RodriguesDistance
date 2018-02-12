package distancianova;

public class DistancesNeighbourhoodTest {
	private static int N = 35;
	private static int[][] img = new int[N][N];
	private static int xc = img[0].length/2, yc = img.length/2;
	
	private static void setImage(final int width, final int height){
		img = new int[height][width];
		xc = img[0].length/2; yc = img.length/2;
	}

	public static void main(String[] args){
		//chebyshev();
		//manhattan();
		//rodrigues();
		//euclidean();
		
		/*
		int times = 0;
		
		
		double start = System.nanoTime();
		
		final int MAX_TIMES = 1000;
		
		while (times < MAX_TIMES){
			//chebyshev();
			manhattan();
			//rodrigues();
			//euclidean();
			times++;
		}
		
		double end = (System.nanoTime() - start)/1000000000f;
		
		
		System.out.println("Elapsed time: " + end/(float)MAX_TIMES);
		*/
		
		//severalSizes();
		justFasterEuclidean();
		
		//fasterEuclidean();
		
		//print();
		
	}
	
	private static void justFasterEuclidean(){
		for (int s=1001; s<10000; s+=1000){
			if (s % 2 == 0) s += 1;
			N = s;
			
			System.out.print(s + ",");
		
			setImage(s, s);
			final int MAX_TIMES = 100;
			
			int times = 0;
			double start = System.nanoTime();
			while (times < MAX_TIMES){
				fasterEuclidean();
				times++;
			}
			double end = (System.nanoTime() - start)/1000000000f;
			
			System.out.println(end/(float)MAX_TIMES + ",");
		}
	}
	
	private static void severalSizes(){
		final int MAX_TIMES = 100;
		
		System.out.println("INIT");
		
		for (int s=1001; s<10000; s+=1000){
			if (s % 2 == 0) s += 1;
			N = s;
			
			System.out.print(s + ",");
		
			setImage(s, s);
			
			int times = 0;
			double start = System.nanoTime();
			while (times < MAX_TIMES){
				chebyshev();
				times++;
			}
			double end = (System.nanoTime() - start)/1000000000f;
			
			System.out.print(end/(float)MAX_TIMES + ",");
			
			times = 0;
			start = System.nanoTime();
			while (times < MAX_TIMES){
				manhattan();
				times++;
			}
			end = (System.nanoTime() - start)/1000000000f;
			
			System.out.print(end/(float)MAX_TIMES + ",");
			
			times = 0;
			start = System.nanoTime();
			while (times < MAX_TIMES){
				euclidean();
				times++;
			}
			end = (System.nanoTime() - start)/1000000000f;
			
			System.out.print(end/(float)MAX_TIMES + ",");
			
			times = 0;
			start = System.nanoTime();
			while (times < MAX_TIMES){
				rodrigues();
				times++;
			}
			end = (System.nanoTime() - start)/1000000000f;
			
			System.out.println(end/(float)MAX_TIMES + ",");
			
		}
		
	}
	
	private static void print(){
		for (int i=0; i<img.length; i++){
			for(int j=0; j<img[0].length; j++){
				String size = "" + img[i][j];
				if (size.length() == 1) size = size + " ";
				System.out.print(size + ",");
			}
			System.out.println();
		}
	}
	
	
	//DISTANCES BELOW
	
	private static void chebyshev(){
		
		int d = 1;
		final int LIMIT = (int) Math.ceil(N/2f);
		while (d < LIMIT){
			for (int l = -d; l <= d; l++){
				setPixel(xc + l, yc - d, d);
				setPixel(xc + l, yc + d, d);
				if (l != d && l != -d){
					setPixel(xc - d, yc + l, d);
					setPixel(xc + d, yc + l, d);
				}
			}
			d++;
		}
		
	}
	
	private static void manhattan(){
		int d = 1;
		
		int xa = 0, ya = 0;
		
		final int LIMIT = (int) Math.ceil(N/2f);
		
		while (d < LIMIT){
			
			//bottom
			xa = xc; ya = yc + d;
			for (int l=0; l<=d; l++){
				setPixel(xa, ya, d);
				xa ++; ya --;
			}
			
			//right
			xa = xc + d; ya = yc;
			for (int l=0; l<=d; l++){
				setPixel(xa, ya, d);
				xa --; ya --;
			}
			
			//top
			xa = xc; ya = yc - d;
			for (int l=0; l<=d; l++){
				setPixel(xa, ya, d);
				xa --; ya ++;
			}
			
			//left
			xa = xc - d; ya = yc;
			for (int l=0; l<=d; l++){
				setPixel(xa, ya, d);
				xa ++; ya ++;
			}
			
			d++;
		}
		
	}
	
	public static void rodrigues(){
		
		int d = 1, dist = d;
		final int LIMIT = (int) Math.ceil(N/2f);
		while (d < LIMIT){
			
			setPixel(xc + d, yc, dist);
			setPixel(xc - d, yc, dist);
			setPixel(xc, yc + d, dist);
			setPixel(xc, yc - d, dist);
			
			for (int l=1; l <= d; l++){
				
				setPixel(xc + d, yc - l, dist + l);
				setPixel(xc + d, yc + l, dist + l);
				
				setPixel(xc - d, yc - l, dist + l);
				setPixel(xc - d, yc + l, dist + l);
				
				if (l != d){
					
					setPixel(xc - l, yc + d, dist + l);
					setPixel(xc + l, yc + d, dist + l);
					
					setPixel(xc - l, yc - d, dist + l);
					setPixel(xc + l, yc - d, dist + l);
					
				}
				
			}
			
			dist += d + 1;
			d++;

		}
		
	}
	
	
	public static void fasterEuclidean(){
		int d = 1;
		
		int xa = 0, ya = 0;
		
		final int LIMIT = (int) Math.ceil(N/2f);
		
		while (d < LIMIT){
			
			for (int l2=0; l2<d/2f; l2++){
				
				boolean foundFirst = false;
				
				for (int l = d; l >= 0; l--){
				
					boolean finished = false;
					
					xa = xc + l; ya = yc - d + l2;
					if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
						setPixel(xa, ya, d);
						foundFirst = true;
					}else
						finished = foundFirst;
					
					ya = yc + d - l2;
					if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
						setPixel(xa, ya, d);
					}
					
					
					if (l != d){
						xa = xc - d + l2; ya = yc + l;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
						}
						
						xa = xc + d - l2;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
						}
					}
					
					//contrario
					if (l == 0) continue;
					
					int l3 = -l;
					xa = xc + l3; ya = yc - d + l2;
					if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
						setPixel(xa, ya, d);
					}
					
					ya = yc + d - l2;
					if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
						setPixel(xa, ya, d);
					}
					
					
					if (l3 != d){
						xa = xc - d + l2; ya = yc + l3;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
						}
						
						xa = xc + d - l2;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
						}
					}
					
					if (finished){
						break;
					}
				}
				
			}
			d++;
		}
		
	}
	
	public static void fasterEuclidean2(){
		int d = 1;
		int xa = 0, ya = 0;
		
		while (d < Math.ceil(N/2f)){
			
			for (int l2=0; l2<d/2f; l2++){
				
				boolean foundFirst = false;
				
				for (int l = d; l >= 0; l--){
					
					boolean finished = false;
				
					for (int ln = l; ln >= -l; ln -= 2*l){//contrario
						
						xa = xc + ln; ya = yc - d + l2;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
							foundFirst = true;
						}else
							finished = foundFirst;
						
						ya = yc + d - l2;
						if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
							setPixel(xa, ya, d);
						}
						
						
						if (ln != d){
							xa = xc - d + l2; ya = yc + ln;
							if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
								setPixel(xa, ya, d);
							}
							
							xa = xc + d - l2;
							if ((int) Math.sqrt(Math.pow(xc - xa, 2) + Math.pow(yc - ya, 2)) == d){
								setPixel(xa, ya, d);
							}
						}
						
						if (ln == 0){
							break;
						}
						
					}
					
					if (finished){
						break;
					}

				}
				
			}
			d++;
		}
		
		
	}
	 
	
	public static void euclidean(){
		
		int d = 1;
		
		int xa = 0, ya = 0;
		while (d < Math.ceil(N/2f)){
			
			for (int l2=0; l2<d/2f; l2++){
				
				for (int l = -d; l <= d; l++){
				
					xa = xc + l; ya = yc - d + l2;
					if (eucDist(xc, yc, xa, ya) == d){
						setPixel(xa, ya, d);
					}
					
					ya = yc + d - l2;
					if (eucDist(xc, yc, xa, ya) == d){
						setPixel(xa, ya, d);
					}
					
					
					if (l != d && l != -d){
						xa = xc - d + l2; ya = yc + l;
						if (eucDist(xc, yc, xa, ya) == d){
							setPixel(xa, ya, d);
						}
						
						xa = xc + d - l2;
						if (eucDist(xc, yc, xa, ya) == d){
							setPixel(xa, ya, d);
						}
					}
					
						
				}
				
			}
			d++;
		}
		
		
	}
	
	private static int eucDist(final int x1, final int y1, final int x2, final int y2){
		return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	private static void setPixel(final int x, final int y, final int value){
		//if (x >= 0 && y >= 0 && y < img.length && x < img[0].length){
			img[y][x] = value;
		//}
	}
	
}
