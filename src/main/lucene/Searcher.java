package main.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * The Searcher class uses a QueryParser from the lucene framework to make
 * a full text search through the indexed Files. It is important that the
 * Analyzer, which is used for process the query input is a StandaradAnalyzer
 * object from the lucene framework, cause the acutal Analyzer from the Indexer
 * class is also a StandardAnalyzer.
 *
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class Searcher {

    /**
     * The search method uses the Queryparser for process a full text search of
     * the indexed files from the indexDir. The resulting Document objects are
     * ranked by the lucene framework. The output is limited to 900000 documents
     * objects per search.
     *
     * The standard field for the query is the 'name' field of the Document objects.
     * This field is provided by the application for the users search query.
     *
     * @param indexDir          String containing the indexdir path
     * @param q                 String containing the actual Query
     * @return                  ArraList of resulting Document objects from the search
     * @throws IOException
     * @throws ParseException
     * @since 1.0
     */
    public static ArrayList<Document> search(String indexDir, String q) throws IOException, ParseException {
        String endtext = "";
        Path path = Paths.get(indexDir);
        IndexReader rdr = DirectoryReader.open(FSDirectory.open(path));
        IndexSearcher is = new IndexSearcher(rdr);

        QueryParser  parser = new QueryParser("name", new StandardAnalyzer());
        Query query = parser.parse(q);

        TopDocs hits = is.search(query, 900000);
        ArrayList<Document> endlist = new ArrayList<>();

        for (ScoreDoc scoreDoc: hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            endlist.add(doc);
        }
        rdr.close();
        return endlist;
    }

}
