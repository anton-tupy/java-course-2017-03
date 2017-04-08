package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.sql.SQLException;

@Log4j
public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: documentIndexer <url>");
			return;
		}

		try {
			RecursiveHtmlIndexer indexer = new RecursiveHtmlIndexer(new Metric[]{new WordCounter(), new ImageCounter()});
			indexer.processHtmlDocument(args[0]);
			HibernateSerializer hibernateSerializer = new HibernateSerializer();
			indexer.serializeMetrics(hibernateSerializer);
			indexer.serializeMetrics(new CSVSerializer());
			hibernateSerializer.close();
		} catch (IOException e) {
			log.warn("IOException " + args[0], e);
		}
	}
}
