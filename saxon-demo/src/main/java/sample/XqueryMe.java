package sample;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XQueryCompiler;
import net.sf.saxon.s9api.XQueryEvaluator;
import net.sf.saxon.s9api.XQueryExecutable;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class XqueryMe {
	
	public static void main(String[] args) throws SaxonApiException, IOException {
		// the Saxon processor object
		Processor saxon = new Processor(false);

		// compile the query
		XQueryCompiler compiler = saxon.newXQueryCompiler();
		XQueryExecutable exec = compiler.compile(new File("src/main/resources/sample.xqy"));

		// parse the string as a document node
		DocumentBuilder builder = saxon.newDocumentBuilder();
		// String input = "<xml>...</xml>";
		// Source src = new StreamSource(new StringReader(input));
		Source src = new StreamSource(new FileReader("src/main/resources/sample.xsl"));
		XdmNode doc = builder.build(src);

		// instantiate the query, bind the input and evaluate
		XQueryEvaluator query = exec.load();
		query.setContextItem(doc);
		XdmValue result = query.evaluate();
		
		System.out.println(result);
	}

}
