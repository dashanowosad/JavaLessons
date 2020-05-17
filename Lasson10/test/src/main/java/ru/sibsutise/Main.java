package ru.sibsutise;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.*;
import org.bson.BasicBSONObject;
import org.bson.Document;

public class Main {
    public static final void main(String args[]){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("users");

        MongoCollection<Document> collection = db.getCollection("users");


        BasicDBObject searchObj = new BasicDBObject();
        searchObj.put("age", new BasicDBObject("$gt", 40));
        BasicDBObject changeObj = new BasicDBObject();
        searchObj.put("$set", 50);

        //Document cursor = collection.find(searchObj);
        //System.out.println(cursor.toString());

        for(Document doc: collection.find(searchObj))
            System.out.println(doc.toJson());


    }
}
