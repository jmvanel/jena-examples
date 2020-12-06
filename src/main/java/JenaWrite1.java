// package sparql_cache;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.jena.query.Dataset;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFWriterRegistry;
import org.apache.jena.riot.WriterDatasetRIOT;
import org.apache.jena.riot.WriterDatasetRIOTFactory;
import org.apache.jena.riot.system.PrefixMapFactory;
import org.apache.jena.sparql.util.Context;
import org.apache.jena.tdb.TDBFactory;

/** Write dataset as TRIG */
public class JenaWrite1 {

	public static void main(String[] args) throws FileNotFoundException {
		Dataset dataset = TDBFactory.createDataset("TDB");
		Map<String, String> prefixMap = dataset.getDefaultModel().getNsPrefixMap();
//		Model model = dataset.getNamedModel("http://[0:0:0:0:0:0:0:1]:9000/ldp/1606166127470-89579129033087");
		WriterDatasetRIOTFactory writerDatasetFactory = RDFWriterRegistry.getWriterDatasetFactory(
				RDFFormat.TRIG);
				// RDFFormat.TURTLE);
				// RDFFormat.TURTLE_PRETTY);
		System.out.println( writerDatasetFactory );
		WriterDatasetRIOT w = writerDatasetFactory.create(RDFFormat.TRIG); // TURTLE_PRETTY);
		OutputStream out =new FileOutputStream("test.ttl");
		w.write(out,
				dataset.asDatasetGraph(),
				PrefixMapFactory.createForOutput(prefixMap), "baseURI", new Context() );
	} 

}
