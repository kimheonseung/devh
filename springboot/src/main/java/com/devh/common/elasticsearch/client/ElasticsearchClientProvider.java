package com.devh.common.elasticsearch.client;

import com.devh.common.exception.PropertiesException;
import com.devh.common.util.PropertiesUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Paths;

/**
 * <pre>
 * Description :
 *     Elasticsearch Java Client 제공자
 *     해당 Client는 자체 Pool 관리를 함
 *     프로그램 시작 시 초기화 된 Client를 쭉 사용
 *     프로그램 종료 시 Client close
 *     속도 개선을 위해 Client를 여러개 생성할 경우 
 *         자체적인 Pool 관리의 한계를 넘어서서 더 느려질 수 있음
 *     고려사항 - Async 관련 커넥션 객체 이용?
 * ===============================
 * Memberfields :
 *     
 * ===============================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021. 12. 29.
 * </pre>
 */
@Slf4j
public class ElasticsearchClientProvider {
	
	private RestClient mRestClient;
	private ElasticsearchTransport mElasticsearchTransport;
	private ElasticsearchClient mElasticsearchClient;
	
	private static ElasticsearchClientProvider instance;
	public static ElasticsearchClientProvider getInstance() {
		if(instance == null)
			instance = new ElasticsearchClientProvider();
		return instance;
	}
	
	private ElasticsearchClientProvider() {
		PropertiesUtils propertiesUtils = new PropertiesUtils(Paths.get("").toAbsolutePath().toString() + File.separator + "conf" + File.separator + "config.properties");
		String esIp;
		int esPort;
		if(propertiesUtils.load()) {
			try {
				esIp   = propertiesUtils.getPropertyValue("elasticsearch.ip");
				esPort = Integer.parseInt(propertiesUtils.getPropertyValue("elasticsearch.port"));
			} catch (PropertiesException e) {
				esIp   = "127.0.0.1";
				esPort = 9200;
			}
		} else {
			esIp   = "127.0.0.1";
			esPort = 9200;
		}

		init(esIp, esPort);
	}
	
	
	/*
	 * <pre>
	 * Description : 
	 *     초기화 설정
	 *     Netty Initailizer 구현체에서 호출하여 IP, PORT로 client 초기화
	 *     이어서 템플릿을 확인
	 * ===============================
	 * Parameters :
	 *     String elasticsearchIp
	 *     int elasticsearchPort
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	private void init(String elasticsearchIp, int elasticsearchPort) {
		/* Create the low-level client */
		this.mRestClient = RestClient.builder(new HttpHost(elasticsearchIp, elasticsearchPort)).build();
		/* Create the transport with a Jackson mapper */
		this.mElasticsearchTransport = new RestClientTransport(this.mRestClient, new JacksonJsonpMapper());
		/* Create the API client */
		this.mElasticsearchClient = new ElasticsearchClient(this.mElasticsearchTransport);
		
		ping(elasticsearchIp, elasticsearchPort);
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     연결객체 제공
	 * ===============================
	 * Parameters :
	 *     
	 * Returns :
	 *     ElasticsearchClient
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	public ElasticsearchClient getClient() {
		if(this.mElasticsearchClient == null)
			log.warn("ElasticsearchClientProvider is not initialized.");
		return this.mElasticsearchClient;
	}
	
	/*
	 * <pre>
	 * Description : 
	 *     연결 확인용 ping
	 *     ping 상태가 false인 경우 정상 수신까지 sleep 하며 connection 재시도
	 * ===============================
	 * Parameters :
	 *     String elasticsearchIp
	 *     int elasticsearchPort
	 * Returns :
	 *     
	 * Throws :
	 *     
	 * ===============================
	 * 
	 * Author : HeonSeung Kim
	 * Date   : 2021. 12. 29.
	 * </pre>
	 */
	private void ping(String elasticsearchIp, int elasticsearchPort) {
		
		boolean isPing = false;
		
		while(!isPing) {
			try {
				isPing = this.mElasticsearchClient.ping().value(); 
				
				if(isPing) {
					log.info(String.format("Success to connect Elasticsearch ! [%s:%d]", elasticsearchIp, elasticsearchPort));
				} else {
					log.warn(String.format("Unable to connect Elasticsearch... [%s:%d] - %s", elasticsearchIp, elasticsearchPort, "ping: false"));
					sleep();
				}
				
			} catch (Exception e) {
				log.warn(String.format("Unable to connect Elasticsearch... [%s:%d] - %s", elasticsearchIp, elasticsearchPort, e.getMessage()));
				sleep();
			}
		}
		
	}
	
	private void sleep() {
		try { Thread.sleep(3000L); } catch (InterruptedException e1) { }
	}
	
	
}
