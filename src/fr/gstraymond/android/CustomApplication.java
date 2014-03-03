package fr.gstraymond.android;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Application;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.gstraymond.biz.ElasticSearchClient;
import fr.gstraymond.cache.BitmapCache;
import fr.gstraymond.hearthstone.card.search.R;

public class CustomApplication extends Application {	
	
	private static final String EN = "en";
	private static final String FR = "fr";

	private static final String TABLET = "tablet";
	
	private static final String SEARCH_SERVER_HOST = "engine.hearthstone-card-search.com:8080";
//	private static final String SEARCH_SERVER_HOST = "local-gsr:9200";
	
	private ElasticSearchClient elasticSearchClient;
	private ObjectMapper objectMapper;
	private Boolean isTablet;
	private BitmapCache bitmapCache; 
	
	public void init() {
		initObjectMapper();
		initElasticSearchClient();
		initIsTablet();
		initBitmapCache();
	}

	private void initElasticSearchClient() {
		try {
			URL url = new URL("http://" + SEARCH_SERVER_HOST + "/hearthstone_" + getLang() + "/card/_search");
			this.elasticSearchClient = new ElasticSearchClient(url, getObjectMapper(), this);
		} catch (MalformedURLException e) {
			Log.e(getClass().getName(), "Error in constructor", e);
		}
	}
	
	private String getLang() {
        String language = getResources().getConfiguration().locale.getLanguage();
        if (FR.equals(language)) {
        	return FR;
        }
        return EN;

	}

	private void initObjectMapper() {
		this.objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	private void initIsTablet() {
		String mode =  getApplicationContext().getString(R.string.mode);
		setTablet(TABLET.equals(mode));
	}

	private void initBitmapCache() {
		setBitmapCache(new BitmapCache());
	}

	public ElasticSearchClient getElasticSearchClient() {
		if (elasticSearchClient == null) {
			initElasticSearchClient();
		}
		return elasticSearchClient;
	}

	public void setElasticSearchClient(ElasticSearchClient elasticSearchClient) {
		this.elasticSearchClient = elasticSearchClient;
	}

	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			initObjectMapper();
		}
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public boolean isTablet() {
		if (isTablet == null) {
			initIsTablet();
		}
		return isTablet;
	}

	public void setTablet(boolean isTablet) {
		this.isTablet = isTablet;
	}

	public BitmapCache getBitmapCache() {
		if (bitmapCache == null) {
			initBitmapCache();
		}
		return bitmapCache;
	}

	public void setBitmapCache(BitmapCache bitmapCache) {
		this.bitmapCache = bitmapCache;
	}

}
