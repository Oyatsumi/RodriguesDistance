package distancianova;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import classifier.lazy.kNearestNeighbourClassifier;
import classifier.rules.ZeroR;
import dataset.Dataset;
import measure.MeanAbsoluteError;
import measure.Measure;
import measure.SumOfSquaredDifference;
import measure.distance.CanberraDistance;
import measure.distance.ChebyshevDistance;
import measure.distance.EuclideanDistance;
import measure.distance.ManhattanDistance;
import measure.distance.MinkowskiDistance;
import measure.distance.RodriguesDistance;
import testmode.kFoldCrossValidation;

public class Classificador {
	
	public static void main(String[] args) throws IOException{
		String path = "C:/Users/oyatsumi/Documents/Outros Projetos/Distancia Nova/datasets/";
		
		File datasetFolder = new File(path + "processar/");
		
		File[] datasets = datasetFolder.listFiles();
		
		boolean skip = false;
		
		for (int da=0; da<datasets.length; da++){
			System.out.println("Processing ... " + datasets[da].getAbsolutePath());
			
			//if (datasets[da].getAbsolutePath().contains("pendigits"))
			//	skip = false;
			
			if (skip) continue;
		
			//Dataset d = new Dataset(path + "iris.arff", 1, 0);
			Dataset d = new Dataset(datasets[da].getAbsolutePath());

			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path + "/results/" + datasets[da].getName() + ".txt")));
			
			//distance array
			Measure[] distances = new Measure[16];
			distances[0] = new EuclideanDistance();
			distances[1] = new ChebyshevDistance();
			distances[2] = new ManhattanDistance();
			distances[3] = new MinkowskiDistance(0.5f);
			distances[4] = new MinkowskiDistance(0.75f);
			distances[5] = new MinkowskiDistance(3);
			distances[6] = new MinkowskiDistance(4);
			distances[7] = new CanberraDistance();
			distances[8] = new MeanAbsoluteError();
			distances[9] = new SumOfSquaredDifference();
			distances[10] = new RodriguesDistance(0.5f);
			distances[11] = new RodriguesDistance(0.75f);
			distances[12] = new RodriguesDistance(1);
			distances[13] = new RodriguesDistance(2);
			distances[14] = new RodriguesDistance(3);
			distances[15] = new RodriguesDistance(4);
	
			for (int di=0; di<distances.length; di++){
				Measure dist = distances[di];
				//System.out.println("Processing for distance: " + dist.getName());
				
				ArrayList<kFoldCrossValidation> results = new ArrayList<kFoldCrossValidation>();
				for (int k=1; k<200; k++){
					System.out.println("k = " + k  + "...");
					
					kFoldCrossValidation kFold = new kFoldCrossValidation(d, new kNearestNeighbourClassifier(k, dist), 10);
					//kFoldCrossValidation kFold = new kFoldCrossValidation(d, new ZeroR(), 10);
					kFold.process(false);
					//kFold.process();
					
					boolean added = false;
					for (int k2=0; k2<results.size(); k2++){
						if (results.get(k2).getAccuracy() < kFold.getAccuracy()){
							results.add(k2, kFold);
							added = true;
							break;
						}
					}
					if (!added) results.add(kFold);
				}
				
				
				//for (int k=0; k<1; k++){
				//	System.out.println(results.get(k).getResultBuffer());
				//}
				System.out.println(results.get(0).getResultBuffer());
				bw.write(results.get(0).getResultBuffer());
				bw.flush();
				
				results.clear();
			}
			
			bw.close();
			
		}
		
	}

}
