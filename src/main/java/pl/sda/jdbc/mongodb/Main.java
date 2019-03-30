package pl.sda.jdbc.mongodb;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("testdb");
            mongoClient.getDatabaseNames().forEach(databaseName -> logger.info(databaseName));

            DBCollection collection = db.getCollection("users");

            // zapisywanie
            BasicDBObject document = new BasicDBObject();
            document.put("firstName", "Jan");
            document.put("lastName", "Kowalski");
            document.put("age", 31);
            collection.insert(document);

            BasicDBObject document2 = new BasicDBObject();
            document2.put("firstName", "Stefan");
            document2.put("lastName", "Nowak");
            document2.put("age", 15);
            collection.insert(document2);

            // wyszukiwanie
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("firstName", "Jan");
            logger.info("Wyniki wyszukiwania:");
            DBCursor dbCursor = collection.find(searchQuery);
            while (dbCursor.hasNext()) {
                logger.info("{}", dbCursor.next());
            }
            logger.info("Koniec wynikow");

            // update
            BasicDBObject dbQuery = new BasicDBObject();
            dbQuery.put("firstName", "Jan");
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("firstName", "Adam");
            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);
            collection.update(dbQuery, updateObj);

            DBCursor findAllCursor = collection.find();
            while (findAllCursor.hasNext()) {
                logger.info("{}", findAllCursor.next());
            }

            // remove
            BasicDBObject query = new BasicDBObject();
            query.put("age", new BasicDBObject("$lt", 18));
            collection.remove(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}