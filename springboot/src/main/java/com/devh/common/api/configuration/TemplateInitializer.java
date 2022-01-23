package com.devh.common.api.configuration;

import com.devh.common.api.entity.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 * Description :
 *     Elasticsearch 템플릿 설정 관련 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/11
 * </pre>
 */
@Component
public class TemplateInitializer {

    private final Logger logger = LoggerFactory.getLogger(TemplateInitializer.class);
    private final ElasticsearchOperations operations;

    public TemplateInitializer(@Qualifier("elasticsearchOperations") ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Autowired
    public void setup() {
        setupNews();
    }

    /**
     * <pre>
     * Description
     *     news index template
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2021-03-22
     * </pre>
     */
    private void setupNews() {
        // {"properties":{"articleId":{"type":"keyword","index":true},"press":{"type":"keyword"},"mainCategory":{"type":"keyword"},"subCategory":{"type":"keyword"},"originalLink":{"type":"keyword"},"pubMillis":{"type":"long"},"title":{"type":"text"},"summary":{"type":"text"}}}
        var indexOps = operations.indexOps(News.class);

        if(!indexOps.existsTemplate("news")) {
            logger.info("Template for news does not exist... creating tempates...");

            var mapping = indexOps.createMapping();

            var aliasActions = new AliasActions().add(
                    new AliasAction.Add(AliasActionParameters.builderForTemplate()
                            .withAliases("news")
                            .build())
            );

            var request = PutTemplateRequest.builder("news", "news_")
                    .withMappings(mapping)
                    .withAliasActions(aliasActions)
                    .build();

            logger.info("Complete to create template... alias : news, index patterns : " + Arrays.toString(request.getIndexPatterns()));
            logger.info(Objects.requireNonNull(request.getMappings().toJson()));

            indexOps.putTemplate(request);

        }
    }
}