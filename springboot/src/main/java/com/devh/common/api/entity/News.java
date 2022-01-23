package com.devh.common.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <pre>
 * Description :
 *     News 엔티티 (Elasticsearch)
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-05-05
 * </pre>
 */
@Document(indexName = "news_#{@newsIndexNameProvider.getSuffix()}", createIndex = false)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class News {
    @Id
    @Field(type = FieldType.Keyword, name = "articleId")
    private String articleId;
    //    @Enumerated(EnumType.STRING)
    @Field(type = FieldType.Keyword, name = "press")
    private String press;
    @Field(type = FieldType.Keyword, name = "mainCategory")
    private String mainCategory;
    @Field(type = FieldType.Keyword, name = "subCategory")
    private String subCategory;
    @Field(type = FieldType.Keyword, name = "originalLink")
    private String originalLink;
    @Field(type = FieldType.Long, name = "pubMillis")
    private Long pubMillis;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Text, name = "summary")
    private String summary;
}