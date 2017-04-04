package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;

@Log4j
public class RecursiveHtmlIndexer extends Indexer {
	static final private int MAX_DEPTH = 1;

	public RecursiveHtmlIndexer(Metric[] metrics) {
		super(metrics);
	}

	public void processHtmlDocument(String url) throws IOException {
		processHtmlDocumentRecursive(url, 0);
	}

	private void processHtmlDocumentRecursive(String url, int depth) throws IOException {
		log.info("Fetching " + url);
		HtmlDocument htmlDocument = new HtmlDocument(url);
		log.info("Processing " + url);
		super.processDocument(htmlDocument);

		if (depth >= MAX_DEPTH)
			return;

		for (String link : htmlDocument.getLinks())
			processHtmlDocumentRecursive(link, depth + 1);
	}
}
