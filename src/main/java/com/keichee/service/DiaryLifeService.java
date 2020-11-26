package com.keichee.service;

import com.keichee.constant.MongoCollections;
import com.keichee.domain.Post;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

@Slf4j
@Service
@AllArgsConstructor
public class DiaryLifeService {

    private final MongoDatabase blogAppDatabase;

    public List<Post> getPosts(Post query) {
        FindIterable<Document> docs;
        MongoCollection<Document> db = blogAppDatabase.getCollection(MongoCollections.DIARY_LIFE);
        if (!ObjectUtils.isEmpty(query.getId())) {
            docs = db.find(eq("_id", new ObjectId(query.getId())));
        } else if (!ObjectUtils.isEmpty(query.getTitle())) {
            docs = db.find(regex("title", query.getTitle()));
        } else {
            docs = blogAppDatabase.getCollection(MongoCollections.DIARY_LIFE).find();
        }
        List<Post> result = new ArrayList<>();

        for (Document doc : docs) {
            result.add(Post.builder()
                    .id(doc.getObjectId("_id").toHexString())
                    .title(doc.getString("title"))
                    .content(doc.getString("content")).build());
        }

        return result;
    }

    public void createPost(Post post) {
        Document doc = new Document();
        doc.put("title", post.getTitle());
        doc.put("content", post.getContent());
        InsertOneResult result = blogAppDatabase.getCollection(MongoCollections.DIARY_LIFE).insertOne(doc);
        if (!result.wasAcknowledged()) {
            log.error("Not acknowledged");
        }
    }

    public void updatePost(Post post) {
        Document doc = new Document();
        doc.put("title", post.getTitle());
        doc.put("content", post.getContent());

        blogAppDatabase.getCollection(MongoCollections.DIARY_LIFE)
                .findOneAndUpdate(new BasicDBObject("_id", post.getId()), new BasicDBObject("$set", doc));
    }

    public void deletePost(String _id) {
        blogAppDatabase.getCollection(MongoCollections.DIARY_LIFE)
                .findOneAndDelete(new BasicDBObject("_id", new ObjectId(_id)));
    }
}
