package com.person.study;


import com.person.study.config.ElasticSearchConfig;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang
 * @version 1.0
 * @date 2022/1/19 10:04
 */
@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引 插入文档
     * @throws IOException
     */
    @Test
    public void test001() throws IOException {
        IndexRequest indexRequest = new IndexRequest("indexrequest_1");
        indexRequest.id(UUID.randomUUID().toString().replaceAll("-",""));
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"name\":\"高先生\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);
        indexRequest.type(XContentType.JSON.mediaType());
        IndexResponse index = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
        DocWriteResponse.Result result = index.getResult();
        if(DocWriteResponse.Result.CREATED.equals(index.getResult())){
            System.out.println("创建索引 插入文档完毕！！result"+"result="+result);
        }
    }

    /**
     * 获取指定索引下的 id 文档
     * @throws IOException
     */
    @Test
    public void test002() throws IOException {
        GetRequest getRequest = new GetRequest("indexrequest");
        getRequest.id("066dc3941ba24ad081fcd65bdba3cdc8");
        GetResponse documentFields = restHighLevelClient.get(getRequest, ElasticSearchConfig.COMMON_OPTIONS);
        Map<String, Object> source = documentFields.getSource();
        System.out.println(source);
    }
    @Test
    public void test003() throws IOException {
        try {
            if(restHighLevelClient.ping(ElasticSearchConfig.COMMON_OPTIONS)){
                System.out.println("链接成功es");
            }
        }catch (Exception e){
            if(e instanceof ElasticsearchException){
                System.out.println("ConnectException链接失败");
            }
        }
    }

    /**
     * 模糊查询
     * @throws IOException
     */
    @Test
    public void test004() throws IOException {
        //创建搜索请求。如果没有参数，这将对所有索引运行。
        SearchRequest searchRequest = new SearchRequest("indexrequest");
        //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "王");
        matchQueryBuilder.fuzziness(Fuzziness.AUTO); //开启模糊性查询
        matchQueryBuilder.prefixLength(3); //模糊前缀
        matchQueryBuilder.maxExpansions(10); //设置最大扩展选项
//        searchSourceBuilder.query(QueryBuilders.matchQuery("text","标题2"));
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }

    /**
     * 高亮查询
     * @throws IOException
     */
    @Test
    public void heihtQueryTest01() throws IOException {
        //指定搜素请求信息
        SearchRequest searchRequest = new SearchRequest("indexrequest"); //index
        //创建搜素源生成器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //匹配
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", "高");
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("name");
        highlightBuilder.preTags("<span style='color:red' >");//设置前缀
        highlightBuilder.postTags("</span>");//设置后缀
        highlightBuilder.field(highlightTitle);
        //设置高亮
        searchSourceBuilder.highlighter(highlightBuilder);
        //匹配器设置匹配规则
        searchSourceBuilder.query(matchQueryBuilder);
        //设置排序
        searchSourceBuilder.sort("postDate");
        //设置分页
        searchSourceBuilder.from(0); //页吗
        searchSourceBuilder.size(10);//默认命中10
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }


    /**
     * 代码创建索引
     */
    @Test
    public void createIndex() {
        try {

            // 创建索引请求对象
            CreateIndexRequest request = new CreateIndexRequest("my_index_23");

            // 设置索引参数
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2));

            // 设置映射
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("name");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();


                    builder.startObject("age");
                    {
                        builder.field("type", "integer");
                    }
                    builder.endObject();

                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping("novel", builder);

            // 执行请求，获取响应
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

            // 输出结果
            boolean acknowledged = response.isAcknowledged();
            System.out.println("Index creation acknowledged: " + acknowledged);

            restHighLevelClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
