import java.io.IOException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

/* Populate TDB default Model */
public class PopulateTDBdefaultModel {

	public static void main(String[] args) throws IOException {
		String tdbDir = "TDB";
		Dataset dataset = TDBFactory.createDataset(tdbDir);
		Model model = RDFDataMgr.loadModel("http://jmvanel.free.fr/jmv.rdf#me");
		System.out.println("Remote RDF retrieved");
		dataset.begin(ReadWrite.WRITE);
		dataset.addNamedModel("urn:x-arq:DefaultGraph", model);
		dataset.commit();
		String[] arg = {"--loc", tdbDir};
		tdb.tdbdump.main( arg );
	} 

}
