package main.lucene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.ext.ExtendableQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Searcher {

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
