package org.egov.infra.mdms.producer;


import org.egov.tracer.kafka.LogAwareKafkaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GitPushProcessProducer {

	public static final Logger logger = LoggerFactory.getLogger(GitPushProcessProducer.class);

	@Autowired
    private LogAwareKafkaTemplate<String, Object> kafkaTemplate;
	
    @Value("${egov.kafka.topics.gitpushprocess}")
    private String topic;
    
    @Value("${egov.kafka.topics.gitpushprocess.key}")
    private String key;

    public void producer(Object value) {
    	logger.info("Value being pushed to the queue: "+value.toString());
    		kafkaTemplate.send(topic, key, value);
    	
    }
}