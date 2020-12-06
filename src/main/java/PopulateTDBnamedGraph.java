
import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

/* Populate TDB named Graph */
public class PopulateTDBnamedGraph {

	public static void main(String[] args) throws IOException {
		String tdbDir = "TDB";
		Dataset dataset = TDBFactory.createDataset(tdbDir);
		String uri = // "http://dbpedia.org/resource/Karst"; // http://dbpedia.org/resource/Aquilegia_vulgaris";
		"https://ontology.uis-speleo.org/example/Gouffre_Jean_Bernard.ttl";
		Model model = RDFDataMgr.loadModel(uri);
		System.out.println("Remote RDF retrieved");
		dataset.begin(ReadWrite.WRITE);
		dataset.addNamedModel(uri, model);
		dataset.commit();
		String[] arg = {"--loc", tdbDir};
		tdb.tdbdump.main( arg );
		System.out.println( model.size() + " triples written to TDB " + tdbDir);
	}

}
