package fr.gstraymond.biz;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.gstraymond.search.model.request.Request;
import fr.gstraymond.search.model.response.SearchResult;
import fr.gstraymond.tools.DisplaySizeUtil;
import fr.gstraymond.tools.MapperUtil;
import fr.gstraymond.tools.VersionUtils;

public class ElasticSearchClient { 
    
	private static final String CONTENT_ENCODING = "Content-Encoding";
	private static final String ACCEPT_ENCODING = "Accept-Encoding";
	private static final String GZIP = "gzip";
	private static final String ENCODING = "UTF-8";
	private URL url;
	private HttpClient httpClient;
	private MapperUtil<SearchResult> mapperUtil;
	private String appVersion;
	private String osVersion;
	
	public ElasticSearchClient(URL url, ObjectMapper objectMapper, Context context) {
		super();
		this.url = url;
		this.httpClient = new DefaultHttpClient();
		this.mapperUtil = new MapperUtil<SearchResult>(objectMapper, SearchResult.class);
		this.appVersion = VersionUtils.getAppVersion(context);
		this.osVersion = VersionUtils.getOsVersion();
	}

	public SearchResult process(SearchOptions options, ProgressBar progressBar) {
		Request request = new Request(options);
		String queryAsJson = mapperUtil.asJsonString(request);
		Log.d(getClass().getName(), "query as json : " + queryAsJson);
		
		try {
			String query = URLEncoder.encode(queryAsJson, ENCODING);
			HttpGet getRequest = buildRequest(query);
			
			long now = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(getRequest);
			String fileSize = getResponseSize(response);
			Log.i(getClass().getName(), "downloaded " + fileSize + " in " + (System.currentTimeMillis() - now) + "ms");
			
			progressBar.setProgress(33);
			InputStream content = getInputStream(response);
			return parse(content, progressBar);
		} catch (IOException e) {
			Log.e(getClass().getName(), "process", e);
		}
		return null;
	}

	private HttpGet buildRequest(String query) {
		HttpGet getRequest = new HttpGet(url.toString() + "?source=" + query);
		getRequest.addHeader(ACCEPT_ENCODING, GZIP);
		getRequest.setHeader("User-Agent", "Android Java/" + osVersion);
		getRequest.setHeader("Referer", "Magic Card Search - " + appVersion);
		return getRequest;
	}

	private String getResponseSize(HttpResponse response) {
		return DisplaySizeUtil.getFileSize(response.getEntity().getContentLength());
	}

	private InputStream getInputStream(HttpResponse response) throws IOException {
		InputStream content = response.getEntity().getContent();
		Header contentEncoding = response.getFirstHeader(CONTENT_ENCODING);
		if (contentEncoding != null && GZIP.equalsIgnoreCase(contentEncoding.getValue())) {
			return new GZIPInputStream(content);
		}
		return content;
	}

	private SearchResult parse(InputStream stream, ProgressBar progressBar) {
		long now = System.currentTimeMillis();
		SearchResult searchResult = mapperUtil.read(stream);
		Log.i(getClass().getName(), "parse took " + (System.currentTimeMillis() - now) + "ms");
		progressBar.setProgress(66);
		return searchResult;
	}
}
