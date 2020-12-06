import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.RDFWriterRegistry;
import org.apache.jena.riot.WriterGraphRIOT;
import org.apache.jena.riot.WriterGraphRIOTFactory;
//import org.apache.jena.riot.system.PrefixMap;
import org.apache.jena.riot.system.PrefixMapFactory;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.util.Context;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.PrefixMappingUtils;

/** Write with PrefixMap, the content of a named graph from TDB 
 * using calcInUsePrefixMapping() from Default Model.
 * PrefixMappingUtils.calcInUsePrefixMapping() analyses the triples,
 * and keeps the prefixes actually used among the given PrefixMapping:
 *
 * Default Model, prefixMap size = 10
 * from calcInUsePrefixMapping(Default Model), prefixMap size = 3
 * */
public class WriteWithPrefixMap3 {

	private static final String OutputFile = "test-graph.ttl";

	public static void main(String[] args) throws IOException {
		Dataset dataset = TDBFactory.createDataset("TDB");

		String uri = // "http://dbpedia.org/resource/Karst";
//		"http://dbpedia.org/resource/Aquilegia_vulgaris";
		"https://ontology.uis-speleo.org/example/Gouffre_Jean_Bernard.ttl";
		dataset.begin(ReadWrite.READ);
		Model model = dataset.getNamedModel(uri);

		PrefixMapping bigPrefixMapping = PrefixMappingUtils.calcInUsePrefixMapping(model.getGraph(),
				dataset.getDefaultModel() );
		System.out.println( "Default Model, prefixMap size = " + dataset.getDefaultModel().getNsPrefixMap().size() );
		System.out.println( "from calcInUsePrefixMapping(Default Model), prefixMap size = " + bigPrefixMapping.numPrefixes() );

		WriterGraphRIOTFactory writerFactory = RDFWriterRegistry.getWriterGraphFactory(
				 RDFFormat.TURTLE_PRETTY);
		WriterGraphRIOT w = writerFactory.create(RDFFormat.TURTLE_PRETTY);
		OutputStream out = new FileOutputStream(OutputFile);
		w.write(out, model.getGraph(),
				PrefixMapFactory.createForOutput(bigPrefixMapping.getNsPrefixMap()),
				"baseURI", new Context() );
		out.close();
		System.out.println( model.size() + " triples written to file " + OutputFile);
		System.out.println( dataset.asDatasetGraph().size() + " total TDB graph size");
		dataset.end();
	} 
}
//Map<String, String> bigPrefixMap = new HashMap<String, String>() {{
//put("a", "b");
//put("c", "d");
//}};
//PrefixMappingImpl
//PrefixMap bigPrefixMapping = PrefixMapFactory.create(bigPrefixMap);
//Map<String, String> prefixMap = // model.getNsPrefixMap();
