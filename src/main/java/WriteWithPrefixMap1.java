import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFWriterRegistry;
import org.apache.jena.riot.WriterGraphRIOT;
import org.apache.jena.riot.WriterGraphRIOTFactory;
import org.apache.jena.riot.system.PrefixMapFactory;
import org.apache.jena.sparql.util.Context;
import org.apache.jena.tdb.TDBFactory;

/* Write with PrefixMap, content of a named graph from TDB */
public class WriteWithPrefixMap1 {

	public static void main(String[] args) throws IOException {
		Dataset dataset = TDBFactory.createDataset("TDB");
		dataset.begin(ReadWrite.READ);
		Map<String, String> prefixMap = dataset.getDefaultModel().getNsPrefixMap();
		System.out.println( "prefixMap size = " + prefixMap.size() );
		Model model = dataset.getNamedModel(
				"http://jmvanel.free.fr/jmv.rdf#me" );
		WriterGraphRIOTFactory writerFactory = RDFWriterRegistry.getWriterGraphFactory(
				 RDFFormat.TURTLE_PRETTY);
		WriterGraphRIOT w = writerFactory.create(RDFFormat.TURTLE_PRETTY);
		OutputStream out = new FileOutputStream("test.ttl");
		w.write(out, model.getGraph(),
				PrefixMapFactory.createForOutput(prefixMap), "baseURI", new Context() );
		out.close();
		System.out.println( model.size() + " written");
		dataset.end();
	} 

}
