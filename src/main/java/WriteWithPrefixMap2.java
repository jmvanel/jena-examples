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

/** Write with PrefixMap, content of a named graph from TDB */
public class WriteWithPrefixMap2 {

	private static final String OutputFile = "test-graph.ttl";

	public static void main(String[] args) throws IOException {
		Dataset dataset = TDBFactory.createDataset("TDB");

		String uri = // "http://dbpedia.org/resource/Karst";
//		"http://dbpedia.org/resource/Aquilegia_vulgaris";
		"https://ontology.uis-speleo.org/example/Gouffre_Jean_Bernard.ttl";

		dataset.begin(ReadWrite.READ);
		Model model = dataset.getNamedModel(uri);
//		PrefixMapping prefixMapping = dataset.getNamedModel(uri);
		Map<String, String> prefixMap = model.getNsPrefixMap();
		System.out.println( "Model from URI <" + uri + "> , prefixMap size = " + prefixMap.size() );

		WriterGraphRIOTFactory writerFactory = RDFWriterRegistry.getWriterGraphFactory(
				 RDFFormat.TURTLE_PRETTY);
		WriterGraphRIOT w = writerFactory.create(RDFFormat.TURTLE_PRETTY);
		OutputStream out = new FileOutputStream(OutputFile);
		w.write(out, model.getGraph(),
				PrefixMapFactory.createForOutput(prefixMap),
				// PrefixMapFactory.create(), // KO, must explicitly add prefixes!
				"baseURI", new Context() );
		out.close();
		System.out.println( model.size() + " triples written to file " + OutputFile);
		System.out.println( dataset.asDatasetGraph().size() + " total TDB graph size");
		dataset.end();
	} 

}
