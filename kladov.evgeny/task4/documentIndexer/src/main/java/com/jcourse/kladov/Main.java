package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;

@Log4j
public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: documentIndexer <url>");
			return;
		}

		try {
			RecursiveHtmlIndexer indexer = new RecursiveHtmlIndexer(new Metric[]{new WordCounter()});
			indexer.processHtmlDocument(args[0]);
			indexer.serializeMetrics(new CSVSerializer());
		} catch (IOException e) {
			log.warn("IOException " + args[0], e);
		}
	}
}
