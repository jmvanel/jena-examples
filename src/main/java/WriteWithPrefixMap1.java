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
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.util.Context;
import org.apache.jena.tdb.TDBFactory;

/** Write with PrefixMap, content of Default Model from TDB */
public class WriteWithPrefixMap1 {

	private static final String OutputFile = "test.ttl";

	public static void main(String[] args) throws IOException {
		Dataset dataset = TDBFactory.createDataset("TDB");
		dataset.begin(ReadWrite.READ);

//		Map<String, String> prefixMap = dataset.getDefaultModel().getNsPrefixMap();
		PrefixMapping prefixMapping = dataset.getDefaultModel(); // getUnionModel();
		Map<String, String> prefixMap = prefixMapping.getNsPrefixMap();
		System.out.println( "prefixMap size = " + prefixMap.size() );

		Model model = dataset.getDefaultModel();
		System.out.println( "Model prefixMap size = " + model.getNsPrefixMap().size() );

		WriterGraphRIOTFactory writerFactory = RDFWriterRegistry.getWriterGraphFactory(
				 RDFFormat.TURTLE_PRETTY);
		WriterGraphRIOT w = writerFactory.create(RDFFormat.TURTLE_PRETTY);
		OutputStream out = new FileOutputStream(OutputFile);
		w.write(out, model.getGraph(),
				PrefixMapFactory.createForOutput(prefixMap), "baseURI", new Context() );
		out.close();
		System.out.println( model.size() + " triples written to file " + OutputFile);
		System.out.println( dataset.asDatasetGraph().size() + " total TDB graph size");
		dataset.end();
	} 

}
