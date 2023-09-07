package com.person.study.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.person.study.config.ElasticSearchConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * es服务类
 * </p>
 *
 * @author gaojuhang
 * @since 2023-09-03
 */
@RestController
@Slf4j
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 获取指定索引下的 id 文档
     *
     * @throws IOException
     */
    @GetMapping("/getbyId")
    public Map getbyId() {
        GetRequest getRequest = new GetRequest("indexrequest");
        getRequest.id("LvMwbooBqWD-zh6W43m4");
        GetResponse documentFields = null;
        try {
            documentFields = restHighLevelClient.get(getRequest, ElasticSearchConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> source = documentFields.getSource();
        log.info("source={}", JSONUtil.toJsonStr(source));
        return source;
    }


    @GetMapping("/conn")
    public boolean conn() {
        boolean res = false;
        try {
            if (res = restHighLevelClient.ping(ElasticSearchConfig.COMMON_OPTIONS)) {
                System.out.println("链接成功es");
            }
        } catch (Exception e) {
            if (e instanceof ElasticsearchException) {
                System.out.println("ConnectException链接失败");
            }
        }
        log.info("res={}", res);
        return res;
    }

    /*模糊查询*/
    @GetMapping("/like")
    public List<Map<String, Object>> like() throws IOException {
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
        List<Map<String, Object>> maps = Arrays.stream(hits).map(i -> {
            Map<String, Object> map = null;
            map = i.getSourceAsMap();
            return map;
        }).collect(Collectors.toList());
        return maps;
    }


    /*高亮查询*/
    @GetMapping("/high")
    public List<Map<String, Object>> high() throws IOException {
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
        List<Map<String, Object>> maps = Arrays.stream(search.getHits().getHits()).map(i -> {
            Map<String, Object> map = null;
            map = i.getSourceAsMap();
            return map;
        }).collect(Collectors.toList());
        return maps;
    }
}

