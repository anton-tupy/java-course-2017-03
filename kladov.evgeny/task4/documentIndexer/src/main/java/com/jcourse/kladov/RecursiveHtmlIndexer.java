package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;

@Log4j
public class RecursiveHtmlIndexer {
	static final private int MAX_DEPTH = 1;
	private Indexer indexer;

	public RecursiveHtmlIndexer(Metric[] metrics) {
		indexer = new Indexer(metrics);
	}

	public void processHtmlDocument(String url) throws IOException {
		try {
			processHtmlDocumentRecursive(url, 0);
		} catch (org.jsoup.UnsupportedMimeTypeException e) {
			log.warn("UnsupportedMimeTypeException", e);
		}
	}

	private void processHtmlDocumentRecursive(String url, int depth) throws IOException {
		log.info("Fetching " + url);
		HtmlDocument htmlDocument = new HtmlDocument(url);
		log.info("Processing " + url);
		indexer.processDocument(htmlDocument);

		if (depth >= MAX_DEPTH)
			return;

		for (String link : htmlDocument.getLinks())
			processHtmlDocumentRecursive(link, depth + 1);
	}

	public void serializeMetrics(MetricSerializer serializer) {
		indexer.serializeMetrics(serializer);
	}
}
