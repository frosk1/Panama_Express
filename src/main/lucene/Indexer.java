package main.lucene;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException{

        //this directory will contain the indexes
        Path path = Paths.get(indexDirectoryPath);
        Directory indexDirectory =
                FSDirectory.open((path));

        //create the indexer
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        writer = new IndexWriter(indexDirectory, cfg);
    }

    public void close() throws CorruptIndexException, IOException{
        writer.close();
    }

    private ArrayList<Document> preprocess_file(File file) throws IOException{

        ArrayList<Document> documents = new ArrayList<>();
        Reader in = new FileReader(file.getAbsolutePath());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        int count =0;

        for (CSVRecord record : records) {
            documents.add(makeDocument(record, file.getName()));
            count +=1;
        }

        in.close();
        System.out.println("loaded File: "+file.getName());
        System.out.println("loaded "+count+" objects");

        return documents;
    }


    private Document makeDocument(CSVRecord record, String filename){
        Document document = new Document();


        if (filename.equals("Addresses.csv")) {
//        address,icij_id,valid_until,country_codes,countries,node_id,sourceID

            document.add(new TextField("type", "addresses", Field.Store.YES));
            document.add(new TextField("name", record.get(0), Field.Store.YES));
//            document.add(new TextField("icij_id", record.get(1), Field.Store.YES));
//            document.add(new TextField("valid_until", record.get(2), Field.Store.YES));
            document.add(new TextField("country_codes", record.get(3), Field.Store.YES));
            document.add(new TextField("countries", record.get(4), Field.Store.YES));
            document.add(new TextField("node_id", record.get(5), Field.Store.YES));
            document.add(new TextField("sourceID", record.get(6), Field.Store.YES));
        }
        else if (filename.equals("Entities.csv")){
//            name,original_name,former_name,jurisdiction,jurisdiction_description,
//            company_type,address,internal_id,incorporation_date,inactivation_date,
//            struck_off_date,dorm_date,status,service_provider,ibcRUC,country_codes,
//            countries,note,valid_until,node_id,sourceID

            document.add(new TextField("type", "entities", Field.Store.YES));
            document.add(new TextField("name", record.get(0), Field.Store.YES));
//            document.add(new TextField("original_name", record.get(1), Field.Store.YES));
//            document.add(new TextField("former_name", record.get(2), Field.Store.YES));
            document.add(new TextField("jurisdiction", record.get(3), Field.Store.YES));
//            document.add(new TextField("jurisdiction_description", record.get(4), Field.Store.YES));
            document.add(new TextField("company_type", record.get(5), Field.Store.YES));
            document.add(new TextField("address", record.get(6), Field.Store.YES));
//            document.add(new TextField("internal_id", record.get(7), Field.Store.YES));

//            document.add(new TextField("incorporation_date", record.get(8), Field.Store.YES));
//            document.add(new TextField("inactivation_date", record.get(9), Field.Store.YES));
//            document.add(new TextField("struck_off_date", record.get(10), Field.Store.YES));
//            document.add(new TextField("dorm_date", record.get(11), Field.Store.YES));
//            document.add(new TextField("status", record.get(12), Field.Store.YES));
//            document.add(new TextField("service_provider", record.get(13), Field.Store.YES));
//            document.add(new TextField("ibcRUC", record.get(14), Field.Store.YES));
            document.add(new TextField("country_codes", record.get(15), Field.Store.YES));

            document.add(new TextField("countries", record.get(16), Field.Store.YES));
//            document.add(new TextField("note", record.get(17), Field.Store.YES));
//            document.add(new TextField("valid_until", record.get(18), Field.Store.YES));
            document.add(new TextField("node_id", record.get(19), Field.Store.YES));
            document.add(new TextField("sourceID", record.get(20), Field.Store.YES));
        }

        else if (filename.equals("Intermediaries.csv")){
//            name,internal_id,address,valid_until,country_codes,countries,status,
//            node_id,sourceID

            document.add(new TextField("type", "intermediaries", Field.Store.YES));
            document.add(new TextField("name", record.get(0), Field.Store.YES));
//            document.add(new TextField("internal_id", record.get(1), Field.Store.YES));
            document.add(new TextField("address", record.get(2), Field.Store.YES));
//            document.add(new TextField("valid_until", record.get(3), Field.Store.YES));
            document.add(new TextField("country_codes", record.get(4), Field.Store.YES));
            document.add(new TextField("countries", record.get(5), Field.Store.YES));
            document.add(new TextField("status", record.get(6), Field.Store.YES));
            document.add(new TextField("node_id", record.get(7), Field.Store.YES));
            document.add(new TextField("sourceID", record.get(8), Field.Store.YES));

        }
        else if (filename.equals("Officers.csv")){
//            name,icij_id,valid_until,country_codes,countries,node_id,sourceID

            document.add(new TextField("type", "officers", Field.Store.YES));
            document.add(new TextField("name", record.get(0), Field.Store.YES));
//            document.add(new TextField("icij_id", record.get(1), Field.Store.YES));
//            document.add(new TextField("valid_until", record.get(2), Field.Store.YES));
            document.add(new TextField("country_codes", record.get(3), Field.Store.YES));
            document.add(new TextField("countries", record.get(4), Field.Store.YES));
            document.add(new TextField("node_id", record.get(5), Field.Store.YES));
            document.add(new TextField("sourceID", record.get(6), Field.Store.YES));

        }
        else if (filename.equals("all_edges.csv")){
//            node_1,rel_type,node_2

            document.add(new TextField("type","relation_node", Field.Store.YES));
            document.add(new TextField("node1", record.get(0),Field.Store.YES));
            document.add(new TextField("relation", record.get(1),Field.Store.YES));
            document.add(new TextField("node2", record.get(2),Field.Store.YES));
        }


        return document;
    }







    private Document getDocument(File file) throws IOException{

        Document document = new Document();

        document.add(new TextField("contents", new FileReader(file)));
        document.add(new TextField("file2", file.getName(),Field.Store.YES));
        System.out.println("asdf: "+file.getName());

        document.add(new StringField("filename",
                file.getName(),
                Field.Store.YES));

        document.add(new StringField("fullpath",
                file.getCanonicalPath(),
                Field.Store.YES));

        return document;
    }

    private void indexFile2(File file) throws IOException{
            ArrayList<Document> documents = this.preprocess_file(file);
            writer.addDocuments(documents);
            documents = null;


        System.out.println("finished indexing");
    }

    private void indexFile(File file) throws IOException{
        System.out.println("Indexing "+file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter)
            throws IOException{
        //get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();

        for (File file : files) {
            if(!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)){

                indexFile2(file);

            }
        }
        return writer.numDocs();
    }
}
