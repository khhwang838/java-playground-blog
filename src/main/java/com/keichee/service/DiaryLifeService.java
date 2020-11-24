package com.keichee.service;

import com.keichee.constant.MongoCollection;
import com.keichee.domain.Post;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DiaryLifeService {

    private final MongoDatabase blogAppDatabase;

    public List<Post> getPosts(Post query) {
        FindIterable<Document> docs;
        if (query == null) {
            docs = blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE).find();
        } else {
            docs = blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE).find(Filters.all("title", query.getTitle())).filter(Filters.all("content", query.getContent()));
        }
        List<Post> result = new ArrayList<>();
        for (Document doc : docs) {

            result.add(Post.builder()
                    ._id(doc.getObjectId("_id").toString())
                    .title(doc.getString("title"))
                    .content(doc.getString("content")).build());
        }
        return result;
    }

    public void createPost(Post post) {
        Document doc = new Document();
        doc.put("title", post.getTitle());
        doc.put("content", post.getContent());
        InsertOneResult result = blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE).insertOne(doc);
        if (!result.wasAcknowledged()) {
            log.error("Not acknowledged");
        }
    }

    public void updatePost(Post post) {
        Document doc = new Document();
        doc.put("title", post.getTitle());
        doc.put("content", post.getContent());

        blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE)
                .findOneAndUpdate(new BasicDBObject("_id", new ObjectId(post.get_id())), new BasicDBObject("$set", doc));
    }

    //    private long getSequenceNextValue(String seqName) {
//        var seqDoc = blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE).findOneAndUpdate({
//                query: { _id: seqName },
//        update: { $inc: { seqValue: 1 } },
//        new: true
//  });
//
//        return seqDoc.seqValue;
//    }

    public void deletePost(String _id) {
        blogAppDatabase.getCollection(MongoCollection.DIARY_LIFE)
                .findOneAndDelete(new BasicDBObject("_id", new ObjectId(_id)));
    }
}
